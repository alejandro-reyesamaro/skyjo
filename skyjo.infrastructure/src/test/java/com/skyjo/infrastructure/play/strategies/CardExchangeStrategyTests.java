package com.skyjo.infrastructure.play.strategies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.skyjo.application.bots.IMinimizer;
import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;
import com.skyjo.infrastructure.test.tools.SkyJoSetDtoTools;

@ExtendWith(MockitoExtension.class)
public class CardExchangeStrategyTests {

    @Mock
    protected IMinimizer minimizer;

    @InjectMocks
    protected CardExchangeStrategy strategy;

    @Test
    public void play_noPlayerToPlay_false() {
        SkyJoSetDto dto = new SkyJoSetDto();
        strategy.mapper = new ModelMapper();
        boolean result = strategy.applies(dto);
        assertThat(result).isFalse();
    }

    @Test
    public void play_0playerSet_false() {
        SkyJoSetDto dto = new SkyJoSetDto();
        dto.setPlayerToPlay("Alex");
        strategy.mapper = new ModelMapper();
        boolean result = strategy.applies(dto);
        assertThat(result).isFalse();
    }

    @Test
    public void applies_noHiddenCard_false() {
        SkyJoSetDto dto = SkyJoSetDtoTools.createPlayerSets(0, 0, 0);
        dto.setPlayerToPlay("Alex");
        strategy.mapper = new ModelMapper();
        boolean result = strategy.applies(dto);
        assertThat(result).isFalse();
    }

    @Test
    public void applies_someHiddenCards_true() {
        SkyJoSetDto dto = SkyJoSetDtoTools.createPlayerSets(10, 8, 10);
        dto.setPlayerToPlay("Alex");
        strategy.mapper = new ModelMapper();
        boolean result = strategy.applies(dto);
        assertThat(result).isTrue();
    }

    @Test
    public void play_playerNotPresent_exception() {
        SkyJoSetDto dto = SkyJoSetDtoTools.createPlayerSets(10, 8, 10);
        dto.setPlayerToPlay("none");
        strategy.mapper = new ModelMapper();
        assertThrows(IllegalStateException.class, () -> strategy.play(dto));
    }
}
