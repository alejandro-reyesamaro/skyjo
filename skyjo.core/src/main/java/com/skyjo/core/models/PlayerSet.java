package com.skyjo.core.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PlayerSet {
    protected Player player;
    protected List<CardColumn> columns;
    protected boolean isPlaying;

    public PlayerSet() {
        player = new Player();
        columns = new ArrayList<>();
        isPlaying = true;
    }

    public PlayerSet(List<CardColumn> columns, Player player) {
        this.columns = columns;
        this.player = player;
        this.isPlaying = true;
    }
}
