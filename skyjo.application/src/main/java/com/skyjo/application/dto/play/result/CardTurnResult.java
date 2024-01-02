package com.skyjo.application.dto.play.result;

import java.util.ArrayList;
import java.util.List;

import com.skyjo.core.models.Card;


public class CardTurnResult {

    protected List<Card> removedColumn;

    public CardTurnResult() {
        removedColumn = new ArrayList<>();
    }

    public CardTurnResult(List<Card> removedColumn) {
        this.removedColumn = removedColumn;
    }

    public List<Card> getPlayedCards(){
        return removedColumn.stream().skip(1).toList();
    }

    public Card getDisCard(){
        return removedColumn.get(0);
    }
}
