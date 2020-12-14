package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.dto.SimpleResultDTO;
import io.bini.poker.pokerdujeudi.model.Ranking;
import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.ranking.RankingService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class RankingRestController {
    private final SessionService sessionService;
    private final PlayerResultService playerResultService;
    private final RankingService rankingService;

    @Autowired
    public RankingRestController(RankingService rankingeService, SessionService sessionService, PlayerResultService playerResultService) {
        this.sessionService = sessionService;
        this.playerResultService = playerResultService;
        this.rankingService = rankingeService;
    }


    @RequestMapping("until/{sessionId}")
    public List<SimpleResultDTO> getRankingUntilSession(@PathVariable Integer sessionId) {
        Session session = sessionService.get(sessionId);
        return this.playerResultService.getCumulatedResultsUntilSession(sessionId, session.getSeason().getSeasonId());
    }

    @RequestMapping("session/{sessionId}")
    public List<Ranking> getRankingsForSession(@PathVariable Long sessionId) {
        return this.rankingService.getSessionRankings(sessionId);
    }

    @RequestMapping("")
    public List<Ranking> getRankings() {
        return this.rankingService.getSessionRankings();
    }
}
