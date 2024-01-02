package com.skyjo.application.bots;

import org.springframework.beans.factory.annotation.Autowired;

import com.skyjo.core.models.Card;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

import com.skyjo.application.arbiters.IArbiter;
import com.skyjo.application.dto.play.result.CardChangeResult;
import com.skyjo.application.dto.protocol.PlayResult;

import com.skyjo.application.extensions.com.skyjo.core.models.PlayerSet.*;
import com.skyjo.application.extensions.com.skyjo.core.models.CardDeck.*;

public class BaseExchangeStrategy {

    @Autowired
    protected IArbiter arbiter;

    protected void play(SkyJoSet set, PlayResult result, Card card) {
        PlayerSet playerSet = set.getPlayerSets().stream()
            .filter(ps -> ps.getPlayer().getName().equals(set.getPlayerToPlay()))
            .findFirst()
            .orElse(null);

        if (playerSet != null) {
            CardChangeResult exchResult = playerSet.changeCard(card, result.getLocation());
            exchResult.getPlayedCards().forEach(c -> set.getCardDeck().disCard(c));
            set.getCardDeck().disCard(exchResult.getDisCard());
            arbiter.setNextPlayer(set);
        } else {
            throw new IllegalStateException("Wrong player to play");
        }
    }
}
