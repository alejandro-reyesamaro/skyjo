package com.skyjo.application.arbiters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.skyjo.core.models.CardColumn;
import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

import com.skyjo.application.dto.play.result.CounterResult;

@Component
public class DefaultArbiter implements IArbiter {

    private static final int MAX_POINTS = 100;
    private static final int MAX_PLAYERS = 8;
    private static final int MIN_PLAYERS = 2;

    @Override
    public int getMaxPoints() {
        return MAX_POINTS;
    }

    @Override
    public int getMinPlayers() {
        return MIN_PLAYERS;
    }

    @Override
    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    @Override
    public boolean isLegalNumberOfPlayers(int playerNb) {
        return playerNb >= getMinPlayers() && playerNb <= getMaxPlayers();
    }

    @Override
    public String playerToStart(List<PlayerSet> playerSets) {
        String playerToPlay = "";
        int maxScore = -4;
        for(PlayerSet ps : playerSets) {
            int sumPoints = sum(ps.getColumns());
            if (sumPoints > maxScore) {
                playerToPlay = ps.getPlayer().getName();
                if(Strings.isEmpty(playerToPlay))
                    throw new IllegalStateException("Null or empty player name");
                maxScore = sumPoints;
            }
        };
        return playerToPlay;
    }

    @Override
    public boolean itsOver(SkyJoSet set) {
        return set.getEvaluationBlock().keySet().stream().anyMatch(k -> set.getEvaluationBlock().get(k) >= getMaxPoints());
    }

    public String getLooser(SkyJoSet set){
        List<PlayerSet> playerSetsCopy = new ArrayList<>(set.getPlayerSets());
        Collections.sort(playerSetsCopy, new Comparator<PlayerSet>(){
            @Override
            public int compare(PlayerSet ps1, PlayerSet ps2) {
                return getPlayerScore(ps2, set).compareTo(getPlayerScore(ps1, set));
            }

        });
        return playerSetsCopy.get(0).getPlayer().getName();
    }

    @Override
    public String getWinner(SkyJoSet set){
        List<PlayerSet> playerSetsCopy = new ArrayList<>(set.getPlayerSets());
        Collections.sort(playerSetsCopy, new Comparator<PlayerSet>(){
            @Override
            public int compare(PlayerSet ps1, PlayerSet ps2) {
                return getPlayerScore(ps1, set).compareTo(getPlayerScore(ps2, set));
            }

        });
        return playerSetsCopy.get(0).getPlayer().getName();
    }

    @Override
    public CounterResult countPoints(SkyJoSet set, String closerPlayer) {
        CounterResult result = new CounterResult();
        Map<String, Integer> roundPoints = new HashMap<>();

        for (PlayerSet playerSet : set.getPlayerSets()) {
            int playerScore = sum(playerSet.getColumns());
            String player = playerSet.getPlayer().getName();

            // Get round points
            roundPoints.put(player, playerScore);

            // Select min-score player
            if (playerScore < result.getMinScore() || (playerScore == result.getMinScore() && result.getWinner().equals(closerPlayer))) {
                result.setMinScore(playerScore);
                result.setWinner(player);
            }

            // Set closer score
            if (closerPlayer == player) {
                result.setCloserScore(playerScore);
            }
        }

        // Aggregate points
        for(Map.Entry<String, Integer> entry : roundPoints.entrySet()) {
            String player = entry.getKey();
            int score = player.equals(closerPlayer) && !closerPlayer.equals(result.getWinner())
                ? 2 * entry.getValue()
                : entry.getValue();
            set.getEvaluationBlock().put(player, score);
        }
        
        return result;
    }

    @Override
    public void setNextPlayer(SkyJoSet set) {
        //PlayerSet[] playerSets = set.getPlayerSets().toArray(new PlayerSet[set.getPlayerSets().size()]);
        String currentPlayer2playName = set.getPlayerToPlay();
        Optional<PlayerSet> currentPlayer2play = set.getPlayerSets().stream()
            .filter(ps -> ps.getPlayer().getName().equals(currentPlayer2playName))
            .findFirst();
        if(currentPlayer2play.isEmpty())
            throw new IllegalStateException("Wrong current Player-to-Play");

        int currentPlayer2playIndex = set.getPlayerSets().indexOf(currentPlayer2play.get());
        String nextPlayer2playName = set.getPlayerSets()
            .get(currentPlayer2playIndex == set.getPlayerSets().size() - 1 ? 0 : currentPlayer2playIndex + 1)
            .getPlayer()
            .getName();
        
        set.setPlayerToPlay(nextPlayer2playName);
        
        if (currentPlayer2playName.equals(set.getPlayerToPlay()))
            throw new IllegalStateException("Wrong next Player-to-Play");
    }

    @Override
    public boolean canDeal(List<Player> players) {
        return !CollectionUtils.isEmpty(players) && isLegalNumberOfPlayers(players.size());
    }

    @Override
    public boolean canDeal(SkyJoSet set) {
        return !CollectionUtils.isEmpty(set.getPlayerSets()) && isLegalNumberOfPlayers(set.getPlayerSets().size());
    }

    private int sum(List<CardColumn> columns){
        return columns
            .stream()
            .flatMap(column -> column.getCards().stream())
            .mapToInt(card -> card.getCard().isShown() ? card.getCard().getValue() : 0)
            .sum();    
    }
    
    private Integer getPlayerScore(PlayerSet playerSet, SkyJoSet set) {
        String player = playerSet.getPlayer().getName();
        Integer score = set.getEvaluationBlock().get(player);
        if (score == null)
            throw new IllegalStateException(String.format("Wrong player name: %s", player));
        return score;
    }
}
