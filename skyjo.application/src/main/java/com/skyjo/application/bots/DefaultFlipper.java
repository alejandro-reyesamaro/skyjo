package com.skyjo.application.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skyjo.application.dto.CardInfo;
import com.skyjo.application.dto.protocol.FlipResult;
import com.skyjo.application.services.ISkyJoAiService;
import com.skyjo.core.models.PlayerSet;

@Component
public class DefaultFlipper implements IFlipper{

    @Autowired
    protected ISkyJoAiService aiPlayer;

    @Override
    public void flipTwoCards(PlayerSet playerSet){
        FlipResult play1 = aiPlayer.flipFirstCard(playerSet.getPlayer().getServiceAddress());
        if (play1.isSuccess()) {
            playerSet.flip(play1.getLocation());
            CardInfo resultCard = playerSet.getCard(play1.getLocation());
            flipSecondCard(playerSet, resultCard);
        }
        else playerSet.setPlaying(false);
    }

    private void flipSecondCard(PlayerSet playerSet, CardInfo resultCard) {
        FlipResult play2 = aiPlayer.flipSecondCard(playerSet.getPlayer().getServiceAddress(), resultCard);
        if (play2.isSuccess()){
            playerSet.flip(play2.getLocation());
        }
        else playerSet.setPlaying(false);
    }
}
