package com.skyjo.application.dto;

import lombok.Data;

@Data
public class CardInfo {
    protected int value;
    protected Location location;

    public CardInfo() {
        this.location = new Location();
    }

    public CardInfo(int value, Location location) {
        this.value = value;
        this.location = location;
    }
}
