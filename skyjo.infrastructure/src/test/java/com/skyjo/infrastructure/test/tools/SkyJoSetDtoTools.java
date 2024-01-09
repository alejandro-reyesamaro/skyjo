package com.skyjo.infrastructure.test.tools;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.skyjo.core.models.PlayerSet;
import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;

public class SkyJoSetDtoTools {

    public static SkyJoSetDto createPlayerSets() {
        return createPlayerSets(12, 12, 12);
    }

    public static SkyJoSetDto createPlayerSets(int... nbHiddenCardsPerPlayer) { 
        List<PlayerSet> playerSets = new ArrayList<PlayerSet>();
        IntStream.range(0, nbHiddenCardsPerPlayer.length)
            .mapToObj(index -> new AbstractMap.SimpleEntry<>(index, nbHiddenCardsPerPlayer[index]))
            .forEach(entry ->
                playerSets.add(PlayerSetTools.createWithHiddenCards(entry.getKey(), true, entry.getValue()))
            );
        SkyJoSetDto set = new SkyJoSetDto();
        set.setPlayerSets(playerSets);
        return set;
    }

    public static SkyJoSetDto createForCountingException(String winner, String looser) {
        return createForCounting(winner, looser);
    }

    public static SkyJoSetDto createForCountingOK(String winner, String looser) {
        SkyJoSetDto set = createForCounting(winner, looser);
        set.setCloserPlayer(Optional.of(winner));
        return set;
    }

    public static SkyJoSetDto createForCloserWinsItsOver(String winner, String looser, int round) {
        SkyJoSetDto set = createForCounting(winner, looser);
        set.setCloserPlayer(Optional.of(winner));
        set.setRound(round);
        return set;
    }

    public static SkyJoSetDto createForOtherWinsItsOver(String winner, String looser, int round) {
        SkyJoSetDto set = createForCounting(winner, looser);
        set.setCloserPlayer(Optional.of(looser));
        set.setRound(round);
        return set;
    }

    public static SkyJoSetDto createForTieItsOver(String closer, String other, int round) {
        SkyJoSetDto set = createTie(closer, other);
        set.setCloserPlayer(Optional.of(closer));
        set.setRound(round);
        return set;
    }

    private static SkyJoSetDto createForCounting(String winner, String looser) {
        List<PlayerSet> playerSets = new ArrayList<PlayerSet>();
        SkyJoSetDto set = new SkyJoSetDto();
        set.setEvaluationBlock(new HashMap<String, Integer>());

        // Winner
        set.getEvaluationBlock().put(winner, 1);
        playerSets.add(PlayerSetTools.createWithTheseCards(winner, new int[] { 1, -1, -2, 0, 2, 1, -1, 0, 1, 0, -1, 1 }));

        // Looser
        set.getEvaluationBlock().put(looser, 78);
        playerSets.add(PlayerSetTools.createWithTheseCards(looser, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));

        set.setPlayerSets(playerSets);
        return set;
    }

    private static SkyJoSetDto createTie(String closer, String other) {
        List<PlayerSet> playerSets = new ArrayList<PlayerSet>();
        SkyJoSetDto set = new SkyJoSetDto();
        set.setEvaluationBlock(new HashMap<String, Integer>());

        // Closer
        set.getEvaluationBlock().put(closer, 78);
        playerSets.add(PlayerSetTools.createWithTheseCards(closer, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));

        // Looser
        set.getEvaluationBlock().put(other, 78);
        playerSets.add(PlayerSetTools.createWithTheseCards(other, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));

        set.setPlayerSets(playerSets);
        return set;
    }
}
