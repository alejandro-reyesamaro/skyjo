package com.skyjo.infrastructure.play.strategies;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skyjo.application.arbiters.IArbiter;
import com.skyjo.application.bots.IFlipper;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;
import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;

@Component
public class FlipperStrategy implements IPlayStrategy {

    @Autowired
    protected IArbiter arbiter;

    @Autowired
    protected IFlipper flipper;

    @Autowired
    protected ModelMapper mapper; 

    @Override
    public boolean applies(SkyJoSetDto set) {
        return arbiter.isLegalNumberOfPlayers(set.getPlayerSets().size()) &&
            mapper.map(set, SkyJoSet.class).allCardsAreHidden();
    }

    @Override
    public SkyJoSetDto play(SkyJoSetDto set) {
        var mappedSet = mapper.map(set, SkyJoSet.class);
        for(PlayerSet playerSet : mappedSet.getPlayerSets()){
            flipper.flipTwoCards(playerSet);
        }
        mappedSet.setPlayerToPlay(arbiter.playerToStart(mappedSet.getPlayerSets()));
        SkyJoSetDto mappedResult = mapper.map(mappedSet, SkyJoSetDto.class);
        mappedResult.setScreenMessages(createScreenMessage(mappedResult.getPlayerToPlay()));
        mappedResult.setRound(set.getRound());
        return mappedResult;
    }

    private List<String> createScreenMessage(String playerToPlay) {
        List<String> messages = new ArrayList<>();
        messages.add(String.format("Player to play: %s", playerToPlay));
        return messages;
    }

}
