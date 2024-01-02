package com.skyjo.application.test.tools;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.skyjo.core.models.Player;

public class PlayerTools {

    public static List<Player> createFakePlayerList(int count) {
        return IntStream.rangeClosed(1, count)
            .mapToObj(PlayerTools::createFakePlayer)
            .collect(Collectors.toList());
    }

    public static Player createFakePlayer(int id){
        var player = new Player();
        player.setId(id);
        player.setName(String.format("Player_%d", id));
        player.setEmail(String.format("email%d@test.fr", id));
        player.setServiceAddress(String.format("localhost/%d", id));
        return player;
    }
}
