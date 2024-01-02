package com.skyjo.application.dto.play.result;

import lombok.Data;

@Data
public class CounterResult {
    protected int minScore;
    protected int closerScore;
    protected String winner;

    public CounterResult() {
        this.minScore = 145;
        this.closerScore = 0;
        this.winner = "";
    }
}
