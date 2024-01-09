package com.skyjo.application.arbiters;

import java.util.List;

import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

import com.skyjo.application.dto.play.result.CounterResult;

public interface IArbiter {

    int getMaxPoints();
    int getMinPlayers();
    int getMaxPlayers();
    boolean isLegalNumberOfPlayers(int playerNb);
    String playerToStart(List<PlayerSet> playerSets);
    boolean itsOver(SkyJoSet set);
    String getLooser(SkyJoSet set);
    String getWinner(SkyJoSet set);
    CounterResult countPoints(SkyJoSet set, String closerPlayer);
    void setNextPlayer(SkyJoSet set);
    boolean canDeal(List<Player> players);
    boolean canDeal(SkyJoSet set);
}
