package com.skyjo.api.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyjo.core.models.Player;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @GetMapping("/{id}")
	public ResponseEntity<Player> getById(@PathVariable int id) {
		return ResponseEntity.of(Optional.empty());
	}
}
