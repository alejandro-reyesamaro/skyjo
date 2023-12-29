package com.skyjo.application.bots;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.skyjo.application.dto.protocol.PlayResult;
import com.skyjo.application.services.ISkyJoAiService;
import com.skyjo.core.models.Card;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

public class PickCardStrategy implements IMinimizerFirstCallStrategy {

    @Autowired
    protected IShuffler shuffler;
    
    @Autowired
    protected ISkyJoAiService aiPlayerService;

    @Autowired
    protected List<IMinimizerSecondCallStrategy> minStrategies;

    @Override
    public boolean applies(PlayResult result) {
        return result.isSuccess() && !result.isKeepTheCard();
    }

    @Override
    public void play(SkyJoSet set, PlayResult result, Card card) {
        PlayerSet playerSet = set.getPlayerSets().stream()
        .filter(ps -> Objects.equals(ps.getPlayer().getName(), set.getPlayerToPlay()))
        .findFirst()
        .orElse(null);

        if (playerSet != null) {
            set.getCardDeck().disCard(card);
            Card fromPile = set.getCardDeck().pickCard(shuffler);
            fromPile.setShown(true);
            PlayResult playFromPile = aiPlayerService.playWithPickedCard(playerSet, fromPile);

            for (IMinimizerSecondCallStrategy strategy : minStrategies){
                if (strategy.applies(playFromPile))
                    strategy.play(set, playFromPile, fromPile);
            }
        }
        else throw new IllegalStateException("Wrong player to play");
    }
}
