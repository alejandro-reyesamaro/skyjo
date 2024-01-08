package com.skyjo.application.arbiters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public boolean isLegalNumberOfPLayers(int playerNb) {
        return playerNb >= getMinPlayers() && playerNb <= getMaxPlayers();
    }

    @Override
    public String playerToStart(List<PlayerSet> playerSets) {
        String playerToPlay = "";
        int maxScore = -4;
        for(PlayerSet ps : playerSets) {
            int sumPoints = Sum(ps.getColumns());
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
    public CounterResult countPoints(SkyJoSet set, String closerPlayer){
        CounterResult result = new CounterResult();
        for (PlayerSet playerSet : set.getPlayerSets())
        {
            int playerScore = playerSet.getColumns()
                .stream()
                .flatMap(column -> column.getCards().stream())
                .mapToInt(card -> card.getCard().getValue())
                .sum();
            String player = playerSet.getPlayer().getName();
            Integer score = getPlayerScore(playerSet, set);

            set.getEvaluationBlock().put(player, score + playerScore);
            if (closerPlayer == player) {
                result.setCloserScore(playerScore);
            }

            if (playerScore < result.getMinScore()) {
                result.setMinScore(playerScore);
                result.setWinner(player);
            }
        }
        return result;
    }

    @Override
    public void setNextPlayer(SkyJoSet set){
        PlayerSet[] playerSets = set.getPlayerSets().toArray(new PlayerSet[0]);
        String previousPlayer2play = set.getPlayerToPlay();
        for (int i = 0; i < playerSets.length; i++){
            if (playerSets[i].getPlayer().getName() == set.getPlayerToPlay()) {
                String nextPlayer = playerSets[i == playerSets.length - 1 ? 0 : i + 1].getPlayer().getName();
                set.setPlayerToPlay(nextPlayer);
                return;
            }
        }
        if (previousPlayer2play.equals(set.getPlayerToPlay()))
            throw new IllegalStateException("Wrong Player to Play");
    }

    @Override
    public boolean canDeal(List<Player> players) {
        return !CollectionUtils.isEmpty(players) && isLegalNumberOfPLayers(players.size());
    }

    @Override
    public boolean canDeal(SkyJoSet set) {
        return !CollectionUtils.isEmpty(set.getPlayerSets()) && isLegalNumberOfPLayers(set.getPlayerSets().size());
    }

    private int Sum(List<CardColumn> columns){
        return columns
            .stream()
            .flatMap(column -> column.getCards().stream())
            .mapToInt(card -> card.getCard().isShown() ? card.getCard().getValue() : 0)
            .sum();
            /*
        int sum = 0;
        for (CardColumn cardColumn : columns) {
            for (TableCard card : cardColumn.getCards()) {
                sum += card.getCard().isShown() ? card.getCard().getValue() : 0;
            }
        }
        return sum;
        */        
    }
    
    private Integer getPlayerScore(PlayerSet playerSet, SkyJoSet set) {
        String player = playerSet.getPlayer().getName();
        Integer score = set.getEvaluationBlock().get(player);
        if (score == null)
            throw new IllegalStateException(String.format("Wrong player name: %s", player));
        return score;
    }
}
