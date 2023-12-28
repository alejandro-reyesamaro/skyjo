package com.skyjo.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.Data;

@Data
public class CardDeck {
    protected Stack<Card> cardsPile;
    protected List<Card> playedCards;
    public Card discard;

    public CardDeck() {
        cardsPile = new Stack<>();
        playedCards = new ArrayList<>();
    }
}
