package com.skyjo.application.bots;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.skyjo.application.dto.protocol.PlayResult;
import com.skyjo.core.models.Card;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

@Component
public class DisablePlayerStrategy implements IMinimizerFirstCallStrategy, IMinimizerSecondCallStrategy {

    @Override
    public boolean applies(PlayResult result) {
        return !result.isSuccess();
    }

    @Override
    public void play(SkyJoSet set, PlayResult result, Card card) {
        PlayerSet playerSet = set.getPlayerSets().stream()
            .filter(ps -> Objects.equals(ps.getPlayer().getName(), set.getPlayerToPlay()))
            .findFirst()
            .orElse(null);

        if(playerSet == null)
            throw new IllegalStateException("Wrong player to play");

        set.getPlayerSets().stream()
            .filter(p -> Objects.equals(p.getPlayer().getName(), playerSet.getPlayer().getName()))
            .findFirst()
            .ifPresent(p -> p.setPlaying(false));
    }
}
