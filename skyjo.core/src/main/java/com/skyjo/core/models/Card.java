package com.skyjo.core.models;

public class Card {
    protected int value;
    protected boolean isShown;

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
