package com.skyjo.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SkyJoSet {
    protected List<PlayerSet> playerSets;
    protected CardDeck cardDeck;
    protected Map<String, Integer> evaluationBlock;
    protected String playerToPlay;

    public SkyJoSet() {
        playerSets = new ArrayList<>();
        cardDeck = new CardDeck();
        evaluationBlock = new HashMap<>();
        playerToPlay = "";
    }
}
