package com.skyjo.application.services;

import com.skyjo.core.models.Card;
import com.skyjo.core.models.PlayerSet;

import com.skyjo.application.dto.CardInfo;
import com.skyjo.application.dto.protocol.FlipResult;
import com.skyjo.application.dto.protocol.PlayResult;

public interface ISkyJoAiService {

    FlipResult flipFirstCard(String serviceUrl);
    FlipResult flipSecondCard(String serviceUrl, CardInfo firstCard);
    PlayResult playWithDisCard(PlayerSet playerSet, Card card);
    PlayResult playWithPickedCard(PlayerSet playerSet, Card card);
}
