package com.skyjo.application.bots;

import com.skyjo.core.models.Card;
import com.skyjo.core.models.SkyJoSet;

import com.skyjo.application.dto.protocol.PlayResult;

public interface IMinimizerFirstCallStrategy {
    boolean applies(PlayResult result);
    void play(SkyJoSet set, PlayResult result, Card card);
}
