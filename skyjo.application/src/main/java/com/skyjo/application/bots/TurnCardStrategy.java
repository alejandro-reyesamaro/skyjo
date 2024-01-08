package com.skyjo.application.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

import com.skyjo.application.arbiters.IArbiter;
import com.skyjo.application.dto.play.result.CardTurnResult;
import com.skyjo.application.dto.protocol.PlayResult;
import com.skyjo.core.models.Card;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

@Component
public class TurnCardStrategy implements IMinimizerSecondCallStrategy {

    @Autowired
    protected IArbiter arbiter;

    @Override
    public boolean applies(PlayResult result) {
        return result.isSuccess() && !result.isKeepTheCard();
    }

    @Override
    public void play(SkyJoSet set, PlayResult result, Card card) {
        PlayerSet playerSet = set.getPlayerSets().stream()
            .filter(ps -> ps.getPlayer().getName() == set.getPlayerToPlay())
            .findFirst()
            .orElse(null);

        if (playerSet != null) {
            set.getCardDeck().disCard(card);
            CardTurnResult turnResult = playerSet.turnCard(result.getLocation());
            turnResult.getPlayedCards().forEach(c -> set.getCardDeck().disCard(c));
            arbiter.setNextPlayer(set);
        }
        else throw new IllegalStateException("Wrong player to play");
    }
}
