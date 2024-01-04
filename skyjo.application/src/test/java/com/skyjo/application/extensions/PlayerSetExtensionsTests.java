package com.skyjo.application.extensions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.skyjo.application.test.tools.PlayerSetTools;
import com.skyjo.application.test.tools.SkyJoSetTools;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.SkyJoSet;

@ExtendWith(MockitoExtension.class)
public class PlayerSetExtensionsTests {

    @InjectMocks
    protected PlayerSet set;

    @Test
    public void hasAnyHiddenCard_No() {
        PlayerSet set = PlayerSetTools.createPlayerSet(0, 0);
        boolean result = set.hasAnyHiddenCard();
        assertThat(result).isFalse();
    }

    @Test
    public void hasAnyHiddenCard_Yes(){
        PlayerSet set = PlayerSetTools.createPlayerSet(0, 2);
        boolean result = set.hasAnyHiddenCard();
        assertThat(result).isTrue();
    }

    @Test
    public void allCardsAreHidden_No() {
        PlayerSet set = PlayerSetTools.createPlayerSet(0, 10);
        boolean result = set.allCardsAreHidden();
        assertThat(result).isFalse();
    }

    @Test
    public void allCardsAreHidden_Yes() {
        PlayerSet set = PlayerSetTools.createPlayerSet(0, 12);
        boolean result = set.allCardsAreHidden();
        assertThat(result).isTrue();
    }

    @Test
    public void allCardsShown_No() {
        PlayerSet set = PlayerSetTools.createPlayerSet(0, 10);
        boolean result = set.allCardsShown();
        assertThat(result).isFalse();
    }

    @Test
    public void allCardsShown_Yes() {
        PlayerSet set = PlayerSetTools.createPlayerSet(0, 0);
        boolean result = set.allCardsShown();
        assertThat(result).isTrue();
    }
}
