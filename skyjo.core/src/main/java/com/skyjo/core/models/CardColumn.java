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
}
