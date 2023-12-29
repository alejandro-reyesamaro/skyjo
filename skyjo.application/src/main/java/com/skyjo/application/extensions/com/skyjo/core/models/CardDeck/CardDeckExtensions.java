package com.skyjo.application.extensions.com.skyjo.core.models.CardDeck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import com.skyjo.application.bots.IShuffler;
import com.skyjo.core.models.Card;
import com.skyjo.core.models.CardDeck;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

@Extension
public class CardDeckExtensions {

    public static void disCard(@This CardDeck deck, Card discard) {
        if (deck.getDiscard() != null)
            deck.getPlayedCards().add(deck.getDiscard());
        deck.setDiscard(discard);
        deck.getDiscard().setShown(true);
    }

    public static void initCardsPile(@This CardDeck deck, IShuffler shuffler){
        ArrayList<Card> cards = new ArrayList<>();
        int[] cardValues = IntStream.rangeClosed(-2, 12 + 3).toArray();
        int[] minusZeroCounts = { 5, 10, 15 };
        int[] cardCounts = IntStream.concat(Arrays.stream(minusZeroCounts), IntStream.range(0, 12).map(i -> 10)).toArray();
        for (int i = 0; i < cardValues.length; i++) {
            addCards(cardCounts[i], cardValues[i], cards);
        }
        shuffler.shuffle(cards); 
        Stack<Card> pile = new Stack<>();
        pile.addAll(cards);
        deck.setCardsPile(pile);
    }

    public static void InitCardDeck(@This CardDeck deck, IShuffler shuffler){
        deck.initCardsPile(shuffler);
        deck.setDiscard(deck.getCardsPile().pop());
        deck.getDiscard().setShown(true);
        deck.setPlayedCards(new ArrayList<Card>());
    }

    public static Card PickCard(@This CardDeck deck, IShuffler shuffler){
        if (deck.getCardsPile().size() <= 0) {
            List<Card> playedCards = deck.getPlayedCards();
            shuffler.shuffle(playedCards);
            Stack<Card> pile = new Stack<Card>();
            pile.addAll(playedCards);
            deck.setCardsPile(pile);
            deck.setPlayedCards(new ArrayList<Card>());
        }
        return deck.getCardsPile().pop();
    }

    public static Card GetDiscard(@This CardDeck deck){
        Card discard = deck.getDiscard();
        if(discard == null) 
            throw new IllegalStateException("Asking for null Dis-Card");
        deck.setDiscard(null);
        return discard;
    }

    public static void DisCard(@This CardDeck deck, Card discard){
        if (deck.getDiscard() != null)
            deck.getPlayedCards().add(deck.getDiscard());
        deck.setDiscard(discard);
        deck.getDiscard().setShown(true);
    }

    private static void addCards(int count, int cardValue, List<Card> cards) {
        for (int i = 0; i < count; i++) {
            cards.add(new Card(cardValue, false));
        }
    }
}
