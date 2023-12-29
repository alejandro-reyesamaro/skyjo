package com.skyjo.application.bots;

import java.util.List;

import com.skyjo.core.models.CardDeck;
import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;

public interface IPlayerSetFactory {
    PlayerSet create(Player player, CardDeck deck);
    List<PlayerSet> createList(List<Player> players, CardDeck deck);
}
