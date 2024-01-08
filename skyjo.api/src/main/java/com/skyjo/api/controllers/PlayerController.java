package com.skyjo.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyjo.api.controllers.response.AddPlayerResponse;
import com.skyjo.api.controllers.response.BaseResponse;
import com.skyjo.api.controllers.strategies.ICrudResponseStrategy;
import com.skyjo.application.dto.form.PlayerForm;
import com.skyjo.application.services.IPlayerService;
import com.skyjo.core.models.Player;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/players")
public class PlayerController {

	@Autowired
	protected IPlayerService service;

	@Autowired
    protected List<ICrudResponseStrategy<AddPlayerResponse>> addStrategies;

    @GetMapping("/{id}")
	public ResponseEntity<Player> getById(@PathVariable int id) {
		return ResponseEntity.of(service.getPlayer(id));
	}

	@GetMapping("")
	public ResponseEntity<List<Player>> getAll() {
		List<Player> players = service.getAllPlayers();
		return new ResponseEntity<List<Player>>(players, OK);
	}

	@GetMapping("is-active")
	public ResponseEntity<List<Player>> getActivePlayers() {
		List<Player> players = service.getActivePlayers();
		return new ResponseEntity<List<Player>>(players, OK);
	}

	@PostMapping("")
	public ResponseEntity<BaseResponse> addPlayer(@Validated @RequestBody PlayerForm body) {
		Player player = service.insertPlayer(body);
		var response = AddPlayerResponse.fromResult(player);
		return CrudControllerHelper.runStrategies(addStrategies, response);
	}
}
