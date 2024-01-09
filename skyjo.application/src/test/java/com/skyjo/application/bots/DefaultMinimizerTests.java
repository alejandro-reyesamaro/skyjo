package com.skyjo.application.bots;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import com.skyjo.core.models.SkyJoSet;

@ExtendWith(MockitoExtension.class)
public class DefaultMinimizerTests {

    @InjectMocks
    protected DefaultMinimizer minimizer;

    @Test
    public void play_nullPlayerToPlay_exception() {
        SkyJoSet set = new SkyJoSet();
        set.setPlayerToPlay(null);
        assertThrows(IllegalArgumentException.class, () -> minimizer.play(set));
    }

    @Test
    public void play_emptyPlayerToPlay_exception() {
        SkyJoSet set = new SkyJoSet();
        set.setPlayerToPlay("");
        assertThrows(IllegalArgumentException.class, () -> minimizer.play(set));
    }
}
