package com.skyjo.application.bots;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.skyjo.core.models.Card;

@Component
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
