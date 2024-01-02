package com.skyjo.application.bots;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import com.skyjo.application.test.tools.CardTools;
import com.skyjo.core.models.Card;

@ExtendWith(MockitoExtension.class)
public class FisherYatesShufflerTests {

    @InjectMocks
    protected FisherYatesShuffler shuffler;

    @Test
    public void shuffle_ShuffleCardList_Success() {
        // Arrange
        List<Card> cards = CardTools.createCardListSample();
        List<Card> notShuffledCards = CardTools.createCardListSample();
        // Act 
        shuffler.shuffle(cards);
        // Assert
        assertThat(cards).isNotEqualTo(notShuffledCards);
    }
}
