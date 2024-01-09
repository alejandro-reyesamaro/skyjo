package com.skyjo.application.arbiters;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.skyjo.application.dto.play.result.CounterResult;
import com.skyjo.application.test.tools.SkyJoSetTools;
import com.skyjo.core.models.SkyJoSet;

@ExtendWith(MockitoExtension.class)
public class DefaultArbiterTests {

    @Test
    public void itsOver_no() {
        SkyJoSet set = SkyJoSetTools.createWithEvaluationBlock(30, 40);
        var arbiter = new DefaultArbiter();
        boolean result = arbiter.itsOver(set);
        assertThat(result).isFalse();
    }

    @Test
    public void itsOver_nes(){
        SkyJoSet set = SkyJoSetTools.createWithEvaluationBlock(130, 40);
        var arbiter = new DefaultArbiter();
        boolean result = arbiter.itsOver(set);
        assertThat(result).isTrue();
    }

    @Test
    public void looser_success(){
        String looser = "Alex";
        SkyJoSet set = SkyJoSetTools.createWithWinnerAndLooserOk("John", looser);
        var arbiter = new DefaultArbiter();
        String result = arbiter.getLooser(set);
        assertThat(result).isEqualTo(looser);
    }

    @Test
    public void winner_success() {
        String winner = "Alex";
        SkyJoSet set = SkyJoSetTools.createWithWinnerAndLooserOk(winner, "John");
        var arbiter = new DefaultArbiter();
        String result = arbiter.getWinner(set);
        assertThat(result).isEqualTo(winner);
    }

    @Test
    public void countPoints_success() {
        // Arrange
        String winner = "Alex";
        String looser = "John";
        SkyJoSet set = SkyJoSetTools.createForCountingOK(winner, looser);
        var arbiter = new DefaultArbiter();

        // Act
        CounterResult result = arbiter.countPoints(set, looser);

        //Assert
        assertThat(result.getWinner()).isEqualTo(winner);
        assertThat(result.getCloserScore()).isEqualByComparingTo(78);
        assertThat(result.getMinScore()).isEqualByComparingTo(1);

        assertThat(set.getEvaluationBlock().get(winner)).isEqualByComparingTo(1);
        assertThat(set.getEvaluationBlock().get(looser)).isEqualByComparingTo(156);
    }

    @Test
    public void setNextPlayer_nextIsSecondOf3_success() {
        String nextPlayer = "John";
        String playerToPlay = "Alex";
        SkyJoSet set = SkyJoSetTools.createForNextPlayer(playerToPlay, playerToPlay, nextPlayer, "Tim");
        var arbiter = new DefaultArbiter();
        arbiter.setNextPlayer(set);
        assertThat(set.getPlayerToPlay()).isEqualTo(nextPlayer);
    }

    @Test
    public void setNextPlayer_nextIsFirstOf3_success(){
        String nextPlayer = "John";
        String playerToPlay = "Alex";
        SkyJoSet set = SkyJoSetTools.createForNextPlayer(playerToPlay, nextPlayer, "Tim", playerToPlay);
        var arbiter = new DefaultArbiter();
        arbiter.setNextPlayer(set);
        assertThat(set.getPlayerToPlay()).isEqualTo(nextPlayer);
    }
}
