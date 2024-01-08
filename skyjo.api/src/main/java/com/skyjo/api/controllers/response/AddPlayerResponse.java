package com.skyjo.api.controllers.response;

import com.skyjo.core.models.Player;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddPlayerResponse extends BaseResponse {

    protected Player newPlayer;

    protected AddPlayerResponse(Player newPlayer, String message, boolean isSuccess) {
        super(message, isSuccess);
        this.newPlayer = newPlayer;
    }

    public static AddPlayerResponse fromResult(Player player) {
        return player == null
            ? new AddPlayerResponse(null, "An error occurred during player creation", false)
            : new AddPlayerResponse(player, String.format("Player %s created successfully", player.getName()), true);
    }
}
