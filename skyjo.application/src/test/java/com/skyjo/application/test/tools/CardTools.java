package com.skyjo.application.test.tools;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.skyjo.core.models.Card;

public class CardTools {

    public static List<Card> createCardListSample()
        {
            return IntStream.rangeClosed(1, 12)
                .mapToObj(i -> new Card(i, false))
                .collect(Collectors.toList());
        }
}
