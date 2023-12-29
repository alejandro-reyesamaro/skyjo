package com.skyjo.core.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CardColumn {
    protected int index;
    protected List<TableCard> cards;

    public CardColumn() {
        cards = new ArrayList<TableCard>();
    }

    public CardColumn(int index, List<TableCard> cards) {
        this.index = index;
        this.cards = cards;
    }
}
