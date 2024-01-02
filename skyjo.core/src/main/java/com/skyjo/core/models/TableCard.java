package com.skyjo.core.models;

import lombok.Data;

@Data
public class TableCard {
    protected int index;
    protected Card card;

    public TableCard(int index, Card card) {
        this.index = index;
        this.card = card;
    }
}
