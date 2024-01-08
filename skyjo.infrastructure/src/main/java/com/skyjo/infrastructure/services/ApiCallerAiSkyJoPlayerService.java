package com.skyjo.infrastructure.services;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.skyjo.application.dto.CardInfo;
import com.skyjo.application.dto.Location;
import com.skyjo.application.dto.protocol.FlipResult;
import com.skyjo.application.dto.protocol.PlayResult;
import com.skyjo.application.services.ISkyJoAiService;
import com.skyjo.core.models.Card;
import com.skyjo.core.models.CardColumn;
import com.skyjo.core.models.PlayerSet;
import com.skyjo.core.models.TableCard;

@Service
public class ApiCallerAiSkyJoPlayerService implements ISkyJoAiService {

    @Override
    public FlipResult flipFirstCard(String serviceUrl) {
        // TODO: call player service API
        Random rand = new Random();
        return new FlipResult(new Location(rand.nextInt(2), rand.nextInt(3)), true);
    }

    @Override
    public FlipResult flipSecondCard(String serviceUrl, CardInfo firstCard) {
        // TODO: call player service API
        Random rand = new Random();
        return new FlipResult(new Location(rand.nextInt(2, 4), rand.nextInt(3)), true);
    }

    @Override
    public PlayResult playWithDisCard(PlayerSet playerSet, Card card) {
        // TODO: call player service API
        return new PlayResult(new Location(), true, false);
    }

    @Override
    public PlayResult playWithPickedCard(PlayerSet playerSet, Card card) {
        // TODO: call player service API
        return new PlayResult(tempFindCardToTurn(playerSet), true, true);
    }

    public Location tempFindCardToTurn(PlayerSet playerSet) {
        for (CardColumn column : playerSet.getColumns()) {
            for (TableCard card : column.getCards()) {
                if (!card.getCard().isShown()) {
                    return new Location(column.getIndex(), card.getIndex());
                }
            }
        }
        throw new IllegalStateException();
    }
}
