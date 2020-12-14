package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.dto.CumulatedPlayerResultDTO;
import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerRestController {
    private final PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping("{playerId}")
    public Player getPlayer(@PathVariable Integer playerId) {
        Optional<Player> maybePlayer = playerService.get(playerId);
        if (maybePlayer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player does not exist");
        }
        return maybePlayer.get();
    }

    @RequestMapping("")
    public List<Player> getPlayers() {
        return playerService.list();
    }
}
