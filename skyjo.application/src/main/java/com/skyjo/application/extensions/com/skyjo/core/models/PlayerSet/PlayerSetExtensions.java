package com.skyjo.application.extensions.com.skyjo.core.models.PlayerSet;

import java.util.ArrayList;
import java.util.List;

import com.skyjo.core.models.Card;
import com.skyjo.core.models.CardColumn;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;
import com.skyjo.core.models.TableCard;
import com.skyjo.application.dto.CardInfo;
import com.skyjo.application.dto.Location;
import com.skyjo.application.dto.play.result.CardChangeResult;
import com.skyjo.application.dto.play.result.CardTurnResult;
import com.skyjo.application.dto.play.result.factory.CardChangeResultFactory;
import com.skyjo.application.dto.play.result.factory.CardTurnResultFactory;

import lombok.Data;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

@Extension
public class PlayerSetExtensions {

    private static final String FLIP_ANSWER_COLUMN_ERROR_MSG = "Invalid column index in flip result";

    @Data
    public static class NewPlayerColumnResult {
        private CardColumn newColumn;
        private Card discard;
    }

    public static CardChangeResult changeCard(@This PlayerSet playerSet, Card newCard, Location playLocation){
        List<CardColumn> newPlayerColumns = new ArrayList<>();
        CardChangeResult result = new CardChangeResult();
        for (CardColumn column : playerSet.getColumns()) {
            if (playLocation.getColumnIndex() != column.getIndex()) {
                newPlayerColumns.add(column);
            }
            else {
                NewPlayerColumnResult newColumnResult = newPlayerColumn(column, newCard, playLocation);
                result.setDiscard(newColumnResult.getDiscard());
                if (allEqual(newColumnResult.getNewColumn())){
                    result = CardChangeResultFactory.BuildFromColumn(newColumnResult.getNewColumn(), newColumnResult.getDiscard());
                }
                else {
                    newPlayerColumns.add(newColumnResult.getNewColumn());
                }
            }
        }
        playerSet.setColumns(newPlayerColumns);
        return result;
    }

    public static CardTurnResult turnCard(@This PlayerSet playerSet, Location playLocation) {
        List<CardColumn> newPlayerColumns = new ArrayList<>();
        CardTurnResult result = new CardTurnResult();

        for (CardColumn column : playerSet.getColumns()) {
            if (playLocation.getColumnIndex() != column.getIndex()) {
                newPlayerColumns.add(column);
            } else {
                CardColumn newColumn = newPlayerColumn(column, playLocation);
                if (allEqual(newColumn)) {
                    result = CardTurnResultFactory.buildFromColumn(newColumn);
                } else {
                    newPlayerColumns.add(newColumn);
                }
            }
        }

        playerSet.setColumns(newPlayerColumns);
        return result;
    }

    public static void flip(@This PlayerSet playerSet, Location location) {
        List<CardColumn> newColumns = new ArrayList<>();
    
        for (CardColumn column : playerSet.getColumns()) {
            if (column.getIndex() == location.getColumnIndex()) {
                newColumns.add(flip(location, column));
            } else {
                newColumns.add(column);
            }
        }
    
        playerSet.setColumns(newColumns);
    }

    public static List<CardColumn> flip(@This PlayerSet playerSet, Location location1, Location location2) {
        List<CardColumn> newColumns = new ArrayList<>();    
        for (CardColumn column : playerSet.getColumns()) {
            if (column.getIndex() == location1.getColumnIndex()) {
                newColumns.add(flip(location1, column));
            } else if (column.getIndex() == location2.getColumnIndex()) {
                newColumns.add(flip(location2, column));
            } else {
                newColumns.add(column);
            }
        }    
        return newColumns;
    }

    public static CardInfo getCard(@This PlayerSet newPlayerSet, Location location) {
        CardColumn column = newPlayerSet.getColumns().stream()
            .filter(col -> col.getIndex() == location.getColumnIndex())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(FLIP_ANSWER_COLUMN_ERROR_MSG));

        TableCard card = column.getCards().stream()
            .filter(c -> c.getIndex() == location.getCardIndex())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(FLIP_ANSWER_COLUMN_ERROR_MSG));

        return new CardInfo(card.getCard().getValue(), location);
    }

    public static boolean hasAnyHiddenCard(@This PlayerSet set) {
        return set.getColumns().stream()
            .anyMatch(col -> col.getCards().stream()
            .anyMatch(card -> !card.getCard().isShown()));
    }

    public static boolean allCardsAreHidden(@This PlayerSet set) {
        return set.getColumns().stream()
            .allMatch(col -> col.getCards().stream()
            .allMatch(card -> !card.getCard().isShown()));
    }

    public static boolean allCardsShown(@This PlayerSet set) {
        return set.getColumns().stream()
            .allMatch(col -> col.getCards().stream()
            .allMatch(card -> card.getCard().isShown()));
    }

    private static boolean allEqual(CardColumn column) {
        return column.getCards().stream().allMatch(c -> c.getCard().isShown()) &&
            column.getCards().stream().noneMatch(c -> c.getCard().getValue() != column.getCards().get(0).getCard().getValue());
    }

    private static CardColumn flip(Location location, CardColumn column) {
        List<TableCard> newCards = new ArrayList<>();    
        for (TableCard card : column.getCards()) {
            TableCard newCard = card.getIndex() == location.getCardIndex() 
                ? new TableCard(card.getIndex(), new Card(card.getCard().getValue(), true)) 
                : card;
            newCards.add(newCard);
        }    
        return new CardColumn(column.getIndex(), newCards);
    }

    /**
    * Column with the new (picked) card
    */
    private static NewPlayerColumnResult newPlayerColumn(CardColumn column, Card picked, Location playLocation) {
        NewPlayerColumnResult result = new NewPlayerColumnResult();
        List<TableCard> newPlayerCards = new ArrayList<>();
        Card cardToDiscard = null;
        for (TableCard card : column.getCards()) {
            if (playLocation.getCardIndex() != card.getIndex()) {
                newPlayerCards.add(card);
            }
            else {
                card.getCard().setShown(true);
                cardToDiscard = card.getCard();
                picked.setShown(true);
                newPlayerCards.add(new TableCard(card.getIndex(), picked));
            }
        }
        if (cardToDiscard == null)
            throw new RuntimeException("Discard not found");
        result.setDiscard(cardToDiscard);
        CardColumn newCardColumn = new CardColumn(column.getIndex(), newPlayerCards);
        result.setNewColumn(newCardColumn);
        return result;
    }

    private static CardColumn newPlayerColumn(CardColumn column, Location playLocation) {
        List<TableCard> newPlayerCards = new ArrayList<>();    
        for (TableCard card : column.getCards()) {
            if (playLocation.getCardIndex() != card.getIndex()) {
                newPlayerCards.add(card);
            } else {
                card.getCard().setShown(true);
                newPlayerCards.add(new TableCard(card.getIndex(), card.getCard()));
            }
        }    
        return new CardColumn(column.getIndex(), newPlayerCards);
    }
}
