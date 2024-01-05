package com.skyjo.infrastructure.test.tools;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;
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

    private static SkyJoSetDto createForCounting(String winner, String looser) {
        List<PlayerSet> playerSets = new ArrayList<PlayerSet>();
        SkyJoSetDto set = new SkyJoSetDto();
        set.setEvaluationBlock(new HashMap<String, Integer>());

        // Winner
        set.getEvaluationBlock().put(winner, 0);
        playerSets.add(PlayerSetTools.createWithTheseCards(winner, new int[] { 1, -1, -2, 0, 2, 1, -1, 0, 1, 0, -1, 1 }));

        // Looser
        set.getEvaluationBlock().put(looser, 0);
        playerSets.add(PlayerSetTools.createWithTheseCards(looser, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));

        set.setPlayerSets(playerSets);
        return set;
    }











    public static SkyJoSet createWithEvaluationBlock(int... scores) {
        SkyJoSet set = new SkyJoSet();
        set.setEvaluationBlock(new HashMap<>());

        IntStream.range(0, scores.length)
            .forEach(index -> set.getEvaluationBlock().put("Player_" + index, scores[index]));

        return set;
    }

    public static SkyJoSet createWithWinnerAndLooserOk(String winner, String looser) {
        return createWithWinnerAndLooser(winner, looser, true);
    }

    public static SkyJoSet createWithWinnerAndLooserFail(String winner, String looser){
        return createWithWinnerAndLooser(winner, looser, false);
    }

    

    

    public static SkyJoSet createForNextPlayer(String playerToPLay, String... players) {
        List<PlayerSet> playerSets = new ArrayList<PlayerSet>();
        for(String player : players){
            Player p = new Player();
            p.setName(player);
            PlayerSet ps = new PlayerSet();
            ps.setPlayer(p);
            playerSets.add(ps);
        }
        var set = new SkyJoSet();
        set.setPlayerSets(playerSets);
        set.setPlayerToPlay(playerToPLay);
        return set;
    }

    public static SkyJoSet createForNotPlayingPlayer(String playerNotPlaying, String... players) {
        List<PlayerSet> playerSets = new ArrayList<PlayerSet>();
        for (String player : players){
            Player p = new Player();
            p.setName(player);
            PlayerSet ps = new PlayerSet();
            ps.setPlayer(p);
            ps.setPlaying(playerNotPlaying != player);
            playerSets.add(ps);
        }
        var set = new SkyJoSet();
        set.setPlayerSets(playerSets);
        return set;
    }

    

    private static SkyJoSet createWithWinnerAndLooser(String winner, String looser, boolean success) {
        List<PlayerSet> playerSets = new ArrayList<>();
        SkyJoSet set = new SkyJoSet();
        set.setEvaluationBlock(new HashMap<>());

        // Winner
        set.getEvaluationBlock().put(winner, 50);
        Player player = new Player();
        player.setName(success ? winner : "unknown");
        playerSets.add(new PlayerSet(Collections.emptyList(), player));

        // Loser
        set.getEvaluationBlock().put(looser, 150);
        Player looserPlayer = new Player();
        looserPlayer.setName(looser);
        playerSets.add(new PlayerSet(Collections.emptyList(), looserPlayer));

        set.setPlayerSets(playerSets);
        return set;
    }
}
