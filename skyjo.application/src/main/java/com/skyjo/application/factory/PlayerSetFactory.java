package com.skyjo.application.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.skyjo.application.bots.IShuffler;
import com.skyjo.core.models.CardColumn;
import com.skyjo.core.models.CardDeck;
import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.TableCard;

public class PlayerSetFactory implements IPlayerSetFactory {

    @Autowired
    protected IShuffler shuffler;

    @Override
    public PlayerSet create(Player player, CardDeck deck) {
        List<CardColumn> columns = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            TableCard[] cards = {
                new TableCard(0, deck.pickCard(shuffler)),
                new TableCard(1, deck.pickCard(shuffler)),
                new TableCard(2, deck.pickCard(shuffler))
            };
            CardColumn cardColumn = new CardColumn(i, Arrays.asList(cards));
            columns.add(cardColumn);
        }
        return new PlayerSet(columns, player);
    }

    @Override
    public List<PlayerSet> createList(List<Player> players, CardDeck deck) {
        List<PlayerSet> playerSets = new ArrayList<>();
        players.forEach(player -> {
            PlayerSet pSet = create(player, deck);
            playerSets.add(pSet);
        });
        return playerSets;
    }
}
