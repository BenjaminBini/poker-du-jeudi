package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.dto.AddSessionDTO;
import io.bini.poker.pokerdujeudi.dto.SessionResultDTO;
import io.bini.poker.pokerdujeudi.model.*;
import io.bini.poker.pokerdujeudi.service.place.PlaceService;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.ranking.RankingService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import io.bini.poker.pokerdujeudi.service.season.SeasonService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class SessionRestController {
    private final SessionService sessionService;
    private final PlayerResultService playerResultService;
    private final PlayerService playerService;
    private final RankingService rankingService;
    private final PlaceService placeService;
    private final SeasonService seasonService;

    public SessionRestController(SessionService sessionService, SeasonService seasonService, PlaceService placeService, PlayerResultService playerResultService, PlayerService playerService, RankingService rankingService) {
        this.sessionService = sessionService;
        this.playerResultService = playerResultService;
        this.playerService = playerService;
        this.rankingService = rankingService;
        this.placeService = placeService;
        this.seasonService = seasonService;
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
        Optional<Ranking> playerRanking = this.rankingService.getSessionRankings((long) sessionId).stream().filter(r -> r.getPlayer().getPlayerId() == playerId).findFirst();
        playerRanking.ifPresent(ranking -> this.rankingService.deleteRanking(ranking.getRankingKey()));
        this.rankingService.computeRankingsForSession(session);
        return session;
    }

    @PostMapping("add")
    public Session addSession(@RequestBody AddSessionDTO addSessionDTO) {
        System.out.println(addSessionDTO.toString());
        Session session = new Session();
        List<PlayerResult> playerResults = new ArrayList<>();
        for (long playerId : addSessionDTO.getPlayerIds()) {
            Optional<Player> player = this.playerService.get(playerId);
            if (player.isPresent()) {
                PlayerResult playerResult = new PlayerResult();
                playerResult.setPlayer(player.get());
                playerResult.setSession(session);
                playerResults.add(playerResult);
            }
        }
        session.setPlayerResults(playerResults);
        session.setDate(addSessionDTO.getDate());
        Optional<Place> place = this.placeService.get(addSessionDTO.getPlaceId());
        place.ifPresent(session::setPlace);

        Optional<Season> season = this.seasonService.get(addSessionDTO.getSeasonId());
        season.ifPresent(session::setSeason);
        this.sessionService.save(session);
        playerResults.forEach(playerResultService::save);
        return session;
    }

    @PostMapping("{sessionId}/delete")
    public void deleteSession(@PathVariable Long sessionId) {
        this.sessionService.delete(sessionId);
    }

}
