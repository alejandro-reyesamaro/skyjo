package com.skyjo.application.bots;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skyjo.application.arbiters.IArbiter;
import com.skyjo.application.factory.IPlayerSetFactory;
import com.skyjo.core.models.CardDeck;
import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

@Component
public class DefaultDealer implements IDealer {

    private static final String EMPTY_PLAYER_LIST_ERROR_MSG = "Empty player list to deal";

    @Autowired
    protected IPlayerSetFactory playerSetFactory;

    @Autowired
    protected IShuffler shuffler;

    @Autowired
    protected IArbiter arbiter;

    @Override
    public SkyJoSet dealToStart(List<Player> players){
        if (!arbiter.canDeal(players))
            throw new IllegalArgumentException(EMPTY_PLAYER_LIST_ERROR_MSG);
        SkyJoSet set = new SkyJoSet();
        deal(set, players);
        set.initEvaluationBook(players);
        return set;
    }

    @Override
    public void deal(SkyJoSet set) {
        if (!arbiter.canDeal(set))
            throw new IllegalArgumentException(EMPTY_PLAYER_LIST_ERROR_MSG);
        deal(set, set.getPlayerSets().stream().map(PlayerSet::getPlayer).collect(Collectors.toList()));
    }

    private void deal(SkyJoSet set, List<Player> players){
        CardDeck deck = new CardDeck();
        deck.initCardDeck(shuffler);
        set.setPlayerSets(playerSetFactory.createList(players, deck));
        set.setCardDeck(deck);
        set.setPlayerToPlay("");
    }
}
