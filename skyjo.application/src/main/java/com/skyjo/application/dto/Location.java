package com.skyjo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    protected int columnIndex;
    protected int cardIndex;

    public Location() {}
}
