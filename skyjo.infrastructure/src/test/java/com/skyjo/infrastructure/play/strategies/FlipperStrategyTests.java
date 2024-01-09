package com.skyjo.infrastructure.play.strategies;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.skyjo.application.arbiters.IArbiter;
import com.skyjo.application.bots.IFlipper;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;
import com.skyjo.infrastructure.test.tools.PlayerSetTools;
import com.skyjo.infrastructure.test.tools.SkyJoSetDtoTools;

@ExtendWith(MockitoExtension.class)
public class FlipperStrategyTests {

    @Mock
    protected IArbiter arbiter;

    @Mock
    protected IFlipper flipper;

    @Mock
    protected ModelMapper mapper;

    @InjectMocks
    protected FlipperStrategy strategy;

    @Test
    public void play_0playerSet_false() {
        SkyJoSetDto dto = new SkyJoSetDto();
        strategy.mapper = new ModelMapper();
        when(arbiter.isLegalNumberOfPlayers(anyInt())).thenReturn(false);
        boolean result = strategy.applies(dto);
        assertThat(result).isFalse();
    }

    @Test
    public void applies_someCardsHidden_false() {
        SkyJoSetDto dto = SkyJoSetDtoTools.createPlayerSets(12, 10, 8);
        strategy.mapper = new ModelMapper();
        when(arbiter.isLegalNumberOfPlayers(anyInt())).thenReturn(true);
        boolean result = strategy.applies(dto);
        assertThat(result).isFalse();
    }

    @Test
    public void applies_allCardsHidden_true() {
        SkyJoSetDto dto = SkyJoSetDtoTools.createPlayerSets(12, 12, 12);
        strategy.mapper = new ModelMapper();
        when(arbiter.isLegalNumberOfPlayers(anyInt())).thenReturn(true);
        boolean result = strategy.applies(dto);
        assertThat(result).isTrue();
    }

    @Test
    public void play_playerSetOk_success() {
        SkyJoSetDto dto = new SkyJoSetDto();
        PlayerSet ps1 = PlayerSetTools.createPlayerSet(1, 10);
        dto.getPlayerSets().add(ps1);
        dto.getPlayerSets().add(PlayerSetTools.createPlayerSet(2, 10));
        dto.setRound(1);
        strategy.mapper = new ModelMapper();

        when(arbiter.playerToStart(any())).thenReturn(ps1.getPlayer().getName());
        SkyJoSetDto result = strategy.play(dto);

        assertThat(result.getPlayerSets().size()).isEqualByComparingTo(2);
        assertThat(result.getPlayerToPlay()).isEqualTo(ps1.getPlayer().getName());
        assertThat(result.getScreenMessages().size()).isEqualByComparingTo(1);
        assertThat(result.getRound()).isEqualByComparingTo(1);
    }
}
