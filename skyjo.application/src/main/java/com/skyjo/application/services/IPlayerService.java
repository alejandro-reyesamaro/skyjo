package com.skyjo.application.services;

import java.util.List;
import java.util.Optional;

import com.skyjo.core.models.Player;

import com.skyjo.application.dto.form.PlayerForm;
import com.skyjo.application.dto.form.UpdatePlayersIsActiveForm;

public interface IPlayerService {

    List<Player> getAllPlayers();
    List<Player> getActivePlayers();
    Optional<Player> getPlayer(int id);
    int updatePlayerIsActive(UpdatePlayersIsActiveForm form);
    int updatePlayer(int playerId, PlayerForm form);
    Player insertPlayer(PlayerForm form);
    int deletePlayer(int playerId);
}
