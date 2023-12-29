package com.skyjo.application.bots;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.skyjo.application.dto.protocol.PlayResult;
import com.skyjo.application.services.ISkyJoAiService;
import com.skyjo.core.models.Card;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

public class DefaultMinimizer implements IMinimizer {

    @Autowired
    protected IShuffler shuffler;

    @Autowired
    protected ISkyJoAiService aiPlayerService;
    
    @Autowired
    protected List<IMinimizerFirstCallStrategy> minStrategies;

    @Override
    public void play(SkyJoSet set){
        if (StringUtils.hasLength(set.getPlayerToPlay())){
            throw new IllegalStateException("Null player (for minimizer0 found)");
        }

        PlayerSet playerSet = set.getPlayerSets().stream()
            .filter(ps -> Objects.equals(ps.getPlayer().getName(), set.getPlayerToPlay()))
            .findFirst()
            .orElse(null);

        if (playerSet != null){
            Card fromDiscard = set.getCardDeck().getDiscard();
            PlayResult resultFromDiscard = aiPlayerService.playWithDisCard(playerSet, fromDiscard);

            for(IMinimizerFirstCallStrategy strategy : minStrategies) {
                if (strategy.applies(resultFromDiscard))
                    strategy.play(set, resultFromDiscard, fromDiscard);
            }
        }
        else throw new IllegalStateException("Wrong player to play");
    }
}
