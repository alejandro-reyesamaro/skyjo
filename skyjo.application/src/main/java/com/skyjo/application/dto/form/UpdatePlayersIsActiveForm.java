package com.skyjo.application.dto.form;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class UpdatePlayersIsActiveForm {
    protected List<Integer> playerIds;
    protected boolean active;

    public UpdatePlayersIsActiveForm() {
        this.playerIds = Collections.emptyList();
        this.active = true;
    }

    public UpdatePlayersIsActiveForm(List<Integer> playerIds, boolean isActive) {
        this.playerIds = playerIds;
        this.active = isActive;
    }
}
