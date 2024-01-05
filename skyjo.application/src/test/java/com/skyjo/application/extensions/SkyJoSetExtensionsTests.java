package com.skyjo.application.extensions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.skyjo.application.test.tools.PlayerTools;
import com.skyjo.application.test.tools.SkyJoSetTools;
import com.skyjo.core.models.Player;
import com.skyjo.core.models.SkyJoSet;

@ExtendWith(MockitoExtension.class)
public class SkyJoSetExtensionsTests {

    @InjectMocks
    protected SkyJoSet set;

    @Test
    public void initEvaluationBook_With3Players_Success() {
        // Arrange 
        List<Player> players = PlayerTools.createFakePlayerList(3);

        // Act 
        set.initEvaluationBook(players);

        // Assert
        assertThat(set.getEvaluationBlock()).isNotNull();
        assertThat(set.getEvaluationBlock().size()).isEqualByComparingTo(3);
        for(Map.Entry<String,Integer> entry : set.getEvaluationBlock().entrySet()) {
            assertThat(entry.getValue()).isEqualByComparingTo(0);
        }
    }

    @Test
    public void hasAnyHiddenCard_No() {
        SkyJoSet set = SkyJoSetTools.createPlayerSets(0, 0, 0);
        boolean result = set.hasAnyHiddenCard();
        assertThat(result).isFalse();
    }

    @Test
    public void hasAnyHiddenCard_Yes(){
        SkyJoSet set = SkyJoSetTools.createPlayerSets(2, 1, 0);
        boolean result = set.hasAnyHiddenCard();
        assertThat(result).isTrue();
    }

    @Test
    public void allCardsAreHidden_No() {
        SkyJoSet set = SkyJoSetTools.createPlayerSets(12, 12, 10);
        boolean result = set.allCardsAreHidden();
        assertThat(result).isFalse();
    }

    @Test
    public void allCardsAreHidden_Yes() {
        SkyJoSet set = SkyJoSetTools.createPlayerSets(12, 12, 12);
        boolean result = set.allCardsAreHidden();
        assertThat(result).isTrue();
    }

    @Test
    public void allCardsShown_No() {
        SkyJoSet set = SkyJoSetTools.createPlayerSets(12, 12, 10);
        boolean result = set.allCardsShown();
        assertThat(result).isFalse();
    }

    @Test
    public void allCardsShown_Yes() {
        SkyJoSet set = SkyJoSetTools.createPlayerSets(0, 0, 0);
        boolean result = set.allCardsShown();
        assertThat(result).isTrue();
    }

    @Test
    public void isPlaying_Exception(){
        SkyJoSet set = SkyJoSetTools.createForNotPlayingPlayer("Alex", "Alex", "John", "Tim");
        assertThrows(IllegalStateException.class, () -> set.isPlaying("Tom"));
    }

    @Test
    public void isPlaying_Yes_Success() {
        String notPlaying = "John";
        String playing = "Alex";
        SkyJoSet set = SkyJoSetTools.createForNotPlayingPlayer(notPlaying, playing, "John", "Tim");
        boolean result = set.isPlaying(playing);
        assertThat(result).isTrue();
    }

    @Test
    public void isPlaying_No_Success() {
        String notPlaying = "John";
        String playing = "Alex";
        SkyJoSet set = SkyJoSetTools.createForNotPlayingPlayer(notPlaying, playing, "John", "Tim");
        boolean result = set.isPlaying(notPlaying);
        assertThat(result).isFalse();
    }
}
