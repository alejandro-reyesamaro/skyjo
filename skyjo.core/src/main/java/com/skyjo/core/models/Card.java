package com.skyjo.core.models;

import lombok.Data;

@Data
public class Card {
    protected int value;
    protected boolean isShown;

    public Card() {
        this.value = 0;
        this.isShown = false;
    }

    public Card(int value, boolean isShown) {
        this.value = value;
        this.isShown = isShown;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        Card otherCard = (Card)obj;
        return value == otherCard.value && isShown == otherCard.isShown;
    }

    @Override
    public int hashCode()
    {
        return isShown ? 100 * value : value;
    }
}
