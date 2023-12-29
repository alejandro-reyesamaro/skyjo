package com.skyjo.application.dto.play.result.factory;

import java.util.ArrayList;
import java.util.List;

import com.skyjo.core.models.Card;
import com.skyjo.core.models.CardColumn;

import com.skyjo.application.dto.play.result.CardChangeResult;

public class CardChangeResultFactory {

    public static CardChangeResult BuildFromColumn(CardColumn column, Card discard)
        {
            List<Card> cards = new ArrayList<>();
            Card newDiscard = column.getCards().get(0).getCard();
            column.getCards().stream().skip(1).toList().forEach(card -> cards.add(card.getCard()));
            cards.add(discard);
            return new CardChangeResult(cards, newDiscard);
        }
}
