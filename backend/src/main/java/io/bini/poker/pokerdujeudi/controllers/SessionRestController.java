package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.dto.SessionResultDTO;
import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.model.PlayerResult;
import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.ranking.RankingService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class SessionRestController {
    private final SessionService sessionService;
    private final PlayerResultService playerResultService;
    private final PlayerService playerService;
    private final RankingService rankingService;

    public SessionRestController(SessionService sessionService, PlayerResultService playerResultService, PlayerService playerService, RankingService rankingService) {
        this.sessionService = sessionService;
        this.playerResultService = playerResultService;
        this.playerService = playerService;
        this.rankingService = rankingService;
    }

    @GetMapping("")
    public List<Session> getSessions() {
        return this.sessionService.list();
    }

    @GetMapping("{sessionId}")
    public Session getSession(@PathVariable long sessionId) {
        return this.sessionService.get(sessionId);
    }

    @GetMapping("latest")
    public Session getLatestSession() {
        return this.sessionService.getLastSession();
    }

    @GetMapping("latest/{season}")
    public Session getLatestSession(@PathVariable String season) {
        return this.sessionService.getLastSession(season);
    }

    @PostMapping("{sessionId}/player/{playerId}")
    public Session updatePlayerResults(@PathVariable long sessionId, @PathVariable long playerId, @RequestBody SessionResultDTO sessionResultDTO) {
        Session session = this.sessionService.get(sessionId);
        Optional<PlayerResult> maybePlayerResult = session.getPlayerResults().stream().filter(s -> s.getPlayer().getPlayerId() == playerId).findFirst();
        if (maybePlayerResult.isEmpty()) {
            Optional<Player> player = this.playerService.get(playerId);
            PlayerResult playerResult = new PlayerResult();
            playerResult.setPlayer(player.get());
            playerResult.setSession(session);
            playerResult.setBuyIns(sessionResultDTO.getBuyIns());
            playerResult.setResult(sessionResultDTO.getResult());
            this.playerResultService.save(playerResult);
            session.getPlayerResults().add(playerResult);
        }
        if (maybePlayerResult.isPresent()) {
            PlayerResult playerResult = maybePlayerResult.get();
            playerResult.setBuyIns(sessionResultDTO.getBuyIns());
            playerResult.setResult(sessionResultDTO.getResult());
        }
        this.sessionService.save(session);

        return session;
    }

    @PostMapping("{sessionId}/player/{playerId}/delete")
    public Session deletePlayerResult(@PathVariable int sessionId, @PathVariable int playerId) {
        this.playerResultService.delete(playerId, sessionId);
        Session session = this.sessionService.get(sessionId);
        session.getPlayerResults().removeIf(r -> r.getPlayer().getPlayerId() == playerId);
        this.rankingService.computeRankingsForSession(session);
        return session;
    }

}
