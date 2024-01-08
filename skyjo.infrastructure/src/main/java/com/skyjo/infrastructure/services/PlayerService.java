package com.skyjo.infrastructure.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyjo.application.dto.form.PlayerForm;
import com.skyjo.application.dto.form.UpdatePlayersIsActiveForm;
import com.skyjo.application.services.IPlayerService;
import com.skyjo.core.models.Player;
import com.skyjo.infrastructure.repository.IPlayerRepository;
import com.skyjo.infrastructure.repository.PlayerEntity;

@Service
public class PlayerService implements IPlayerService {

    @Autowired
    protected IPlayerRepository repository;

    @Autowired
    protected ModelMapper mapper; 

    @Override
    public void deletePlayer(int playerId) {
        repository.deleteById(Long.valueOf(playerId));
    }

    @Override
    public List<Player> getActivePlayers() {
        List<PlayerEntity> players = repository.findByIsActive(true);
        return players.stream().map(c -> mapper.map(c, Player.class)).collect(Collectors.toList());
    }

    @Override
    public List<Player> getAllPlayers() {
        List<PlayerEntity> players = repository.findAll();
        return players.stream().map(c -> mapper.map(c, Player.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<Player> getPlayer(int id) {
        Optional<PlayerEntity> player = repository.findById(Long.valueOf(id));
        return player.isPresent()
            ? Optional.of(mapper.map(player.get(), Player.class))
            : Optional.empty();
    }

    @Override
    public Player insertPlayer(PlayerForm form) {
        PlayerEntity player = repository.save(new PlayerEntity(form.getName(), form.getEmail(), form.getServiceAddress()));
        return mapper.map(player, Player.class);
    }

    @Override
    public Optional<Player> updatePlayer(int playerId, PlayerForm form) {
        Optional<PlayerEntity> player = repository.findById(Long.valueOf(playerId));
        if(player.isPresent()) {
            PlayerEntity entity = player.get();
            entity.setName(form.getName());
            entity.setEmail(form.getEmail());
            entity.setServiceAddress(form.getServiceAddress());
            return Optional.of(mapper.map(repository.save(entity), Player.class));
        }
        else 
            return Optional.empty();
    }

    @Override
    public int updatePlayerIsActive(UpdatePlayersIsActiveForm form) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePlayerIsActive'");
    }
}
