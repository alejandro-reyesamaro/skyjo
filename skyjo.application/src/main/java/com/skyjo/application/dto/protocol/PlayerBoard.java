package com.skyjo.application.dto.protocol;

import java.util.Collections;
import java.util.List;

import com.skyjo.application.dto.CardInfo;
import lombok.Data;

@Data
public class PlayerBoard {
    /**
     * Player name
     */
    protected String playerName;

    /**
     * Only visible cards
     */
    protected List<CardInfo> cards;

    public PlayerBoard() {
        this.playerName = "";
        this.cards = Collections.emptyList();
    }

    public PlayerBoard(String playerName, List<CardInfo> cards) {
        this.playerName = playerName;
        this.cards = cards;
    }
}
