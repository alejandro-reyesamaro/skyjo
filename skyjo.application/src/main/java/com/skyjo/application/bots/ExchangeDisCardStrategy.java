package com.skyjo.application.bots;

import com.skyjo.application.dto.protocol.PlayResult;
import com.skyjo.core.models.Card;
import com.skyjo.core.models.SkyJoSet;

public class ExchangeDisCardStrategy extends BaseExchangeStrategy implements IMinimizerFirstCallStrategy {

    @Override
    public boolean applies(PlayResult result) {
        return result.isSuccess() && result.isKeepTheCard();
    }

    @Override
    public void play(SkyJoSet set, PlayResult result, Card card) {
        super.play(set, result, card);
    }
}
