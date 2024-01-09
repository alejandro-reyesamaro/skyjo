package com.skyjo.api.controllers;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyjo.application.bots.IDealer;
import com.skyjo.application.services.IPlayerService;
import com.skyjo.core.models.Player;
import com.skyjo.infrastructure.play.dtos.SkyJoSetDto;
import com.skyjo.infrastructure.play.strategies.IPlayStrategy;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/skyjo")
public class SkyJoController {

    @Autowired
    protected ModelMapper mapper;

    @Autowired
    protected IPlayerService playerService;

    @Autowired
    protected IDealer dealer;

    @Autowired
    protected List<IPlayStrategy> strategies;

    @GetMapping("start")
    public ResponseEntity<SkyJoSetDto> start(SkyJoSetDto skyJoSet) {
        List<Player> players = playerService.getActivePlayers();
        var set = dealer.dealToStart(players);
        var mappedSet = mapper.map(set, SkyJoSetDto.class);
        mappedSet.setRound(1);
        return new ResponseEntity<>(mappedSet, OK);
    }

    @PostMapping("next")
	public ResponseEntity<SkyJoSetDto> next(@Valid @RequestBody SkyJoSetDto skyJoSet) {
        for(IPlayStrategy s : strategies) {
            if(s.applies(skyJoSet)) {
                return new ResponseEntity<>(s.play(skyJoSet), OK) ;
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
