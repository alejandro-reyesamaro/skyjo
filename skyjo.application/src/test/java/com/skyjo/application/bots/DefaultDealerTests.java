package com.skyjo.application.bots;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.skyjo.application.arbiters.IArbiter;
import com.skyjo.application.factory.IPlayerSetFactory;
import com.skyjo.application.test.tools.PlayerSetTools;
import com.skyjo.application.test.tools.PlayerTools;
import com.skyjo.core.models.Player;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

@ExtendWith(MockitoExtension.class)
public class DefaultDealerTests {

    @Mock
    protected IPlayerSetFactory playerSetFactory;

    @Mock
    protected IShuffler shuffler;

    @Mock
    protected IArbiter arbiter;

    @InjectMocks
    protected DefaultDealer dealer;

    @Test
    public void dealToStart_emptyPlayerList_trowsException(){
        assertThrows(IllegalArgumentException.class, () -> dealer.dealToStart(new ArrayList<Player>()));
    }

    @Test
    public void dealToStart_1PLayer_trowsException(){
        assertThrows(IllegalArgumentException.class, () -> dealer.dealToStart(PlayerTools.createFakePlayerList(1)));
    }

    @Test
    public void dealToStart_9PLayer_trowsException(){
        assertThrows(IllegalArgumentException.class, () -> dealer.dealToStart(PlayerTools.createFakePlayerList(9)));
    }

    @Test
    public void dealToStart_2PLayer_dealSuccess() {
        // Arrange 
        List<Player> players = PlayerTools.createFakePlayerList(2);
        when(arbiter.canDeal(players)).thenReturn(true);

        // Act
        SkyJoSet result = dealer.dealToStart(players);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCardDeck()).isNotNull();
        assertThat(result.getPlayerToPlay()).isEmpty();
        verify(playerSetFactory, times(1)).createList(anyList(), any());
        verify(shuffler, times(1)).shuffle(anyList());
    }

    @Test
    public void deal_2PLayer_dealSuccess(){
        // Arrange 
        List<Player> players = PlayerTools.createFakePlayerList(2);
        SkyJoSet set = new SkyJoSet();
        set.setPlayerSets(players.stream().map(p -> new PlayerSet(Collections.emptyList(), p)).collect(Collectors.toList()));
        set.initEvaluationBook(players);
        when(arbiter.canDeal(set)).thenReturn(true);

        // Act
        dealer.deal(set);

        // Assert
        assertThat(set.getCardDeck()).isNotNull();
        assertThat(set.getPlayerToPlay()).isEmpty();
        verify(playerSetFactory, times(1)).createList(anyList(), any());
        verify(shuffler, times(1)).shuffle(anyList());
    }
}
