package com.skyjo.infrastructure.play.strategies;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.skyjo.application.arbiters.IArbiter;
import com.skyjo.application.bots.IDealer;
import com.skyjo.application.dto.play.result.CounterResult;
import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;
import com.skyjo.infrastructure.test.tools.SkyJoSetDtoTools;

@ExtendWith(MockitoExtension.class)
public class RoundCounterStrategyTests {

    @Mock
    protected IDealer dealer;

    @Mock
    protected IArbiter arbiter;

    @InjectMocks
    protected RoundCounterStrategy strategy;

    @Test
    public void applies_someCardsHidden_false() {
        SkyJoSetDto dto = SkyJoSetDtoTools.createPlayerSets(12, 10, 8);
        strategy.mapper = new ModelMapper();
        boolean result = strategy.applies(dto);
        assertThat(result).isFalse();
    }

    @Test
    public void applies_someCardsHidden_true() {
        SkyJoSetDto dto = SkyJoSetDtoTools.createPlayerSets(0, 0, 0);
        strategy.mapper = new ModelMapper();
        boolean result = strategy.applies(dto);
        assertThat(result).isTrue();
    }

    @Test
    public void play_noCloserPlayer_exception() {
        SkyJoSetDto dto = SkyJoSetDtoTools.createForCountingException("Alex", "John");
        strategy.mapper = new ModelMapper();
        assertThrows(IllegalArgumentException.class, () -> strategy.play(dto));
    }

    @Test
    public void play_closerWins_itsOver() {
        String winner = "Alex";
        SkyJoSetDto dto = SkyJoSetDtoTools.createForCloserWinsItsOver(winner, "John", 1);
        CounterResult counterResult = new CounterResult();
        counterResult.setMinScore(10);
        counterResult.setCloserScore(10);
        when(arbiter.countPoints(any(), any())).thenReturn(counterResult);
        when(arbiter.itsOver(any())).thenReturn(true);
        counterResult.setWinner(winner);
        strategy.mapper = new ModelMapper();

        SkyJoSetDto result = strategy.play(dto);

        assertThat(result.getScreenMessages().size()).isEqualByComparingTo(4);
        assertThat(result.getScreenMessages().get(0)).startsWith(winner + " wins the round");
        assertThat(result.getCloserPlayer().get()).isEqualTo(winner);
        assertThat(result.getPlayerToPlay()).isEmpty();
        assertThat(result.getRound()).isEqualByComparingTo(0);
    }

    @Test
    public void play_otherWins_itsOver() {
        String winner = "Alex";
        String looser = "John";
        SkyJoSetDto dto = SkyJoSetDtoTools.createForOtherWinsItsOver(winner, looser, 1);
        CounterResult counterResult = new CounterResult();
        counterResult.setMinScore(10);
        counterResult.setCloserScore(12);
        when(arbiter.countPoints(any(), any())).thenReturn(counterResult);
        when(arbiter.itsOver(any())).thenReturn(true);
        counterResult.setWinner(winner);
        strategy.mapper = new ModelMapper();

        SkyJoSetDto result = strategy.play(dto);

        assertThat(result.getScreenMessages().size()).isEqualByComparingTo(5);
        assertThat(result.getCloserPlayer().get()).isEqualTo(looser);
        assertThat(result.getPlayerToPlay()).isEmpty();
        assertThat(result.getRound()).isEqualByComparingTo(0);
    }

    @Test
    public void play_closerWins_itsNotOver() {
        String winner = "Alex";
        SkyJoSetDto dto = SkyJoSetDtoTools.createForCloserWinsItsOver(winner, "John", 1);
        CounterResult counterResult = new CounterResult();
        counterResult.setMinScore(10);
        counterResult.setCloserScore(10);
        when(arbiter.countPoints(any(), any())).thenReturn(counterResult);
        when(arbiter.itsOver(any())).thenReturn(false);
        counterResult.setWinner(winner);
        strategy.mapper = new ModelMapper();

        SkyJoSetDto result = strategy.play(dto);

        assertThat(result.getScreenMessages().size()).isEqualByComparingTo(1);
        assertThat(result.getScreenMessages().get(0)).startsWith(winner + " wins the round");
        assertThat(result.getRound()).isEqualByComparingTo(2);
    }

    @Test
    public void play_otherWins_itsNotOver() {
        String winner = "Alex";
        String looser = "John";
        SkyJoSetDto dto = SkyJoSetDtoTools.createForOtherWinsItsOver(winner, looser, 1);
        CounterResult counterResult = new CounterResult();
        counterResult.setMinScore(10);
        counterResult.setCloserScore(12);
        when(arbiter.countPoints(any(), any())).thenReturn(counterResult);
        when(arbiter.itsOver(any())).thenReturn(false);
        counterResult.setWinner(winner);
        strategy.mapper = new ModelMapper();

        SkyJoSetDto result = strategy.play(dto);

        assertThat(result.getScreenMessages().size()).isEqualByComparingTo(2);
        assertThat(result.getRound()).isEqualByComparingTo(2);
    }
}
