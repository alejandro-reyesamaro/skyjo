package com.skyjo.infrastructure.play.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.skyjo.core.models.CardDeck;
import com.skyjo.core.models.PlayerSet;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class SkyJoSetDto {

    @NotEmpty
    protected List<PlayerSet> playerSets;

    @NotNull
    protected CardDeck cardDeck;

    @NotEmpty
    protected Map<String, Integer> evaluationBlock;

    protected String playerToPlay;

    //!- Infra
    protected Optional<String> closerPlayer;
    protected List<String> screenMessages;

    @Min(value = 0)
    protected int round;

    public SkyJoSetDto() {
        playerSets = new ArrayList<>();
        cardDeck = new CardDeck();
        evaluationBlock = new HashMap<>();
        playerToPlay = "";
        screenMessages = new ArrayList<>();
    }
}
