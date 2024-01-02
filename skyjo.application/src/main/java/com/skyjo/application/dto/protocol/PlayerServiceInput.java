package com.skyjo.application.dto.protocol;

import java.util.Collections;
import java.util.List;

import com.skyjo.application.dto.CardInfo;
import lombok.Data;

@Data
public class PlayerServiceInput {
    protected List<CardInfo> yourBoard;
    protected List<PlayerBoard> boards;

    public PlayerServiceInput() {
        this.yourBoard = Collections.emptyList();
        this.boards = Collections.emptyList();
    }

    public PlayerServiceInput(List<CardInfo> yourBoard, List<PlayerBoard> boards) {
        this.yourBoard = yourBoard;
        this.boards = boards;
    }
}
