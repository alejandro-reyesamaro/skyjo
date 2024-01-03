package com.skyjo.infrastructure.play.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.skyjo.application.arbiters.IArbiter;
import com.skyjo.application.bots.IDealer;
import com.skyjo.application.dto.play.result.CounterResult;
import com.skyjo.core.models.CardDeck;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;
import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;

public class RoundCounterStrategy implements IPlayStrategy {

    @Autowired
    protected IDealer dealer;

    @Autowired
    protected ModelMapper mapper;

    @Autowired
    protected IArbiter arbiter; 

    @Override
    public boolean applies(SkyJoSetDto set) {
        return mapper.map(set, SkyJoSet.class).allCardsShown();
    }

    @Override
    public SkyJoSetDto play(SkyJoSetDto set) {
        int round = set.getRound();

        SkyJoSet skyJoSet = mapper.map(set, SkyJoSet.class);
        if(!set.getCloserPlayer().isPresent())
            throw new IllegalArgumentException("Null or empty closer player for counting");

        CounterResult result = arbiter.countPoints(skyJoSet, set.getCloserPlayer().get());

        List<String> messages = new ArrayList<>();
        if (result.getCloserScore() < result.getMinScore()) {
            // Closer wins:
            messages.add(buildRoundWinnerMessage(set.getCloserPlayer().get(), round));
        }
        else {
            // Other player wins:
            // Closer player doubles its score 
            if(result.getCloserScore() > 0) {
                int currentScore = skyJoSet.getEvaluationBlock().get(set.getCloserPlayer().get());
                skyJoSet.getEvaluationBlock().put(set.getCloserPlayer().get(), currentScore + result.getCloserScore());
            }
            messages.add(buildRoundWinnerMessage(result.getWinner(), round));
            messages.add(String.format("%s doubles its score", set.getCloserPlayer().get()));
        }

        if (arbiter.itsOver(skyJoSet)) {
            messages.add(String.format("Winner: %s", arbiter.getWinner(skyJoSet)));
            messages.add(String.format("Looser: %s", arbiter.getLooser(skyJoSet)));
            messages.add("Game Over");
            var newSkyJoSet = new SkyJoSetDto();
            newSkyJoSet.setCardDeck(new CardDeck());
            newSkyJoSet.setCloserPlayer(set.getCloserPlayer());
            newSkyJoSet.setEvaluationBlock(skyJoSet.getEvaluationBlock());
            newSkyJoSet.setPlayerSets(set.getPlayerSets().stream().map(ps -> new PlayerSet(Collections.emptyList(), ps.getPlayer())).collect(Collectors.toList()));
            newSkyJoSet.setPlayerToPlay("");
            newSkyJoSet.setRound(0);
            newSkyJoSet.setScreenMessages(messages);
            return newSkyJoSet;
        }
        else {
            dealer.deal(skyJoSet);
            set = mapper.map(skyJoSet, SkyJoSetDto.class);
            set.setScreenMessages(messages);
            set.setRound(round + 1);
            return set;
        }
    }

    private String buildRoundWinnerMessage(String player, int round) {
        return String.format("%s wins the round %d", player, round);
    }
}
