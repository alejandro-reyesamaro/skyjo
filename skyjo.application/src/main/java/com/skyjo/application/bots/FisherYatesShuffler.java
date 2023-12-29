package com.skyjo.application.bots;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.skyjo.core.models.Card;

public class FisherYatesShuffler implements IShuffler {

    @Override
    public void shuffle(List<Card> cards) {
        Random random = new Random();
        int n = cards.size();
        while (n > 1) {
            int k = random.nextInt(n--);
            Collections.swap(cards, n, k);
        }
    }
}
