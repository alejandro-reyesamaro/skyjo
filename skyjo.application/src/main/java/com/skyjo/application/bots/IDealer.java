package com.skyjo.application.bots;

import java.util.List;

import com.skyjo.core.models.Player;
import com.skyjo.core.models.SkyJoSet;

public interface IDealer {
    SkyJoSet dealToStart(List<Player> players);
    void deal(SkyJoSet set);
}
