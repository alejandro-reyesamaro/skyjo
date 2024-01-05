package com.skyjo.application.extensions.com.skyjo.core.models.SkyJoSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

@Extension
public class SkyJoSetExtensions {

    public static void initEvaluationBook(@This SkyJoSet set, List<Player> players) {
        Map<String, Integer> block = new HashMap<>();
        players.forEach(player -> {
            if(player.getName() == null)
                throw new IllegalStateException("Player without name found");
            block.put(player.getName(), 0);
        });
        set.setEvaluationBlock(block);
    }

    public static boolean hasAnyHiddenCard(@This SkyJoSet set) {
        return set.getPlayerSets().stream().anyMatch(ps -> ps.hasAnyHiddenCard());
    }

    public static boolean allCardsAreHidden(@This SkyJoSet set) {
        return set.getPlayerSets().stream().allMatch(ps -> ps.allCardsAreHidden());
    }

    public static boolean allCardsShown(@This SkyJoSet set) {
        return set.getPlayerSets().stream().allMatch(ps -> ps.allCardsShown());
    }

    public static boolean isPlaying(@This SkyJoSet set, String player) {
        return set.getPlayerSets().stream()
            .filter(p -> Objects.equals(p.getPlayer().getName(), player))
            .findFirst()
            .map(PlayerSet::isPlaying)
            .orElseThrow(() -> new IllegalStateException("Wrong player name"));
    }
}
