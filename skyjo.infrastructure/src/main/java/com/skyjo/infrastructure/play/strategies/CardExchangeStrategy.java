package com.skyjo.infrastructure.play.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.skyjo.application.bots.IMinimizer;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;
import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;
import com.skyjo.application.extensions.com.skyjo.core.models.SkyJoSet.*;

import io.micrometer.common.util.StringUtils;

public class CardExchangeStrategy implements IPlayStrategy {

    private static final String WRONG_PLAYER_TO_PLAY_ERROR_MSG = "Wrong player to play";

    @Autowired
    protected IMinimizer minimizer;

    @Autowired
    protected ModelMapper mapper; 

    @Override
    public boolean applies(SkyJoSetDto set) {
        return StringUtils.isNotEmpty(set.getPlayerToPlay()) && mapper.map(set, SkyJoSet.class).hasAnyHiddenCard();
    }

    @Override
    public SkyJoSetDto play(SkyJoSetDto set) {
        SkyJoSet mappedSet = mapper.map(set, SkyJoSet.class);
        minimizer.play(mappedSet);
        SkyJoSetDto mappedResult = mapper.map(mappedSet, SkyJoSetDto.class);

        Optional<PlayerSet> playerSet = set.getPlayerSets().stream()
            .filter(ps -> ps.getPlayer().getName() == set.getPlayerToPlay())
            .findFirst();
        if (playerSet.isPresent())
        {
            String closer = !set.getCloserPlayer().isPresent() && currentPlayerSetClosed(set)
                    ? playerSet.get().getPlayer().getName()
                    : set.getCloserPlayer().get();

            mappedResult.setScreenMessages(createScreenMessage(mappedResult.getPlayerSets(), mappedResult.getPlayerToPlay(), closer));
            mappedResult.setRound(set.getRound());
            mappedResult.setCloserPlayer(Optional.of(closer));

            return mappedResult;
        }
        else throw new IllegalStateException(WRONG_PLAYER_TO_PLAY_ERROR_MSG);
    }

    private boolean currentPlayerSetClosed(SkyJoSetDto set) {
        Optional<PlayerSet> playerSet = set.getPlayerSets().stream()
            .filter(ps -> ps.getPlayer().getName().equals(set.getPlayerToPlay()))
            .findFirst();
        if (playerSet.isPresent()) {
            return playerSet.get().getColumns().stream()
                .allMatch(col -> col.getCards().stream()
                .allMatch(card -> card.getCard().isShown()));
        }
        else throw new IllegalStateException(WRONG_PLAYER_TO_PLAY_ERROR_MSG);
    }
    
    private List<String> createScreenMessage(List<PlayerSet> playerSets, String nextPlayer, String closerPlayer) {
        List<String> messages = new ArrayList<>();
        if (playerSets.stream().allMatch(ps -> ps.getColumns().stream().allMatch(col -> col.getCards().stream().allMatch(card -> card.getCard().isShown())))){
            messages.add("The round is over");
            return messages;
        }
        messages.add(String.format("Close player: %s", closerPlayer));
        messages.add(String.format("Player's turn: %s", nextPlayer));
        return messages;
    }
}
