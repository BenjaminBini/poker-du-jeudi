package io.bini.poker.pokerdujeudi.controllers.rest;

import io.bini.poker.pokerdujeudi.dto.SimpleResultDTO;
import io.bini.poker.pokerdujeudi.dto.StatDTO;
import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.player.PlayerRepository;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ranking")
public class RankingRestController {
    private final SessionService sessionService;
    private final PlayerResultService playerResultService;

    @Autowired
    public RankingRestController(SessionService sessionService, PlayerResultService playerResultService) {
        this.sessionService = sessionService;
        this.playerResultService = playerResultService;
    }


    @RequestMapping("until/{sessionId}")
    public List<SimpleResultDTO> getRankingUntilSession(@PathVariable Integer sessionId) {
        Optional<Session> session = sessionService.get(sessionId);
        if (session.isEmpty()) {
            return new ArrayList<>();
        }
        return this.playerResultService.getCumulatedResultsUntilSession(sessionId, session.get().getSeason().getSeasonId());
    }
}
