package com.skyjo.application.bots;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.skyjo.application.dto.Location;
import com.skyjo.application.dto.protocol.FlipResult;
import com.skyjo.application.services.ISkyJoAiService;
import com.skyjo.application.test.tools.PlayerSetTools;
import com.skyjo.core.models.PlayerSet;

@ExtendWith(MockitoExtension.class)
public class DefaultFlipperTests {

    @Mock
    protected ISkyJoAiService aiPlayer;

    @InjectMocks
    protected DefaultFlipper flipper;

    @Test
    public void FlipTowCards_TimeoutFirstFlip_PlayerOut() {
        // Arrange
        var playerSet = createPlayerSet4Test();
        when(aiPlayer.flipFirstCard(playerSet.getPlayer().getServiceAddress()))
            .thenReturn(new FlipResult(new Location(), false));
        // Act
        flipper.flipTwoCards(playerSet);
        // Assert
        assertThat(playerSet.isPlaying()).isFalse();
        verify(aiPlayer, times(1)).flipFirstCard(anyString());
        verify(aiPlayer, times(0)).flipSecondCard(anyString(), any());
    }

    @Test
    public void flipTowCards_TimeoutSecondFlip_PlayerOut() {
        // Arrange
        var playerSet = createPlayerSet4Test();
        when(aiPlayer.flipFirstCard(playerSet.getPlayer().getServiceAddress()))
            .thenReturn(new FlipResult(new Location(), true));
        when(aiPlayer.flipSecondCard(eq(playerSet.getPlayer().getServiceAddress()), any()))
            .thenReturn(new FlipResult(new Location(), false));
        // Act
        flipper.flipTwoCards(playerSet);
        // Assert
        assertThat(playerSet.isPlaying()).isFalse();
        verify(aiPlayer, times(1)).flipFirstCard(anyString());
        verify(aiPlayer, times(1)).flipSecondCard(anyString(), any());
    }

    @Test
    public void flipTowCards_BothResponding_Success() {
        // Arrange
        var playerSet = createPlayerSet4Test();
        var location1 = new Location(0, 0);
        var location2 = new Location(1, 1);
        when(aiPlayer.flipFirstCard(playerSet.getPlayer().getServiceAddress()))
            .thenReturn(new FlipResult(location1, true));
        when(aiPlayer.flipSecondCard(eq(playerSet.getPlayer().getServiceAddress()), any()))
            .thenReturn(new FlipResult(location2, true));
        // Act
        flipper.flipTwoCards(playerSet);
        // Assert
        assertThat(playerSet.isPlaying()).isTrue();
        verify(aiPlayer, times(1)).flipFirstCard(anyString());
        verify(aiPlayer, times(1)).flipSecondCard(anyString(), any());
        assertThat(playerSet.getColumns().stream()
            .flatMapToInt(col -> col.getCards().stream()
                .mapToInt(card -> card.getCard().isShown() ? 1 : 0))
            .sum()).isEqualByComparingTo(2);
    }

    private PlayerSet createPlayerSet4Test() {
        return PlayerSetTools.createWithTheseCards("Alex", new int[] {
            1, 2, 3,
            2, 3, 4, 
            3, 4, 5,
            4, 5, 6
        }, false);
    }
}
