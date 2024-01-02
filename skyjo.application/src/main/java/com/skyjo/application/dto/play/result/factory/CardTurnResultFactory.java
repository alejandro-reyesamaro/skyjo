package com.skyjo.application.dto.play.result.factory;

import java.util.ArrayList;
import java.util.List;

import com.skyjo.core.models.Card;
import com.skyjo.core.models.CardColumn;

import com.skyjo.application.dto.play.result.CardTurnResult;

public class CardTurnResultFactory {
    public static CardTurnResult buildFromColumn(CardColumn column) 
        { 
            List<Card> cards = new ArrayList<>();
            column.getCards().forEach(card -> cards.add(card.getCard()));
            return new CardTurnResult(cards);
        }
}
