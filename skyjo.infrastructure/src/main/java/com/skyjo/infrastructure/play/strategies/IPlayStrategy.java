package com.skyjo.infrastructure.play.strategies;

import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;

public interface IPlayStrategy {
    boolean applies(SkyJoSetDto set);
    SkyJoSetDto play(SkyJoSetDto set);
}
