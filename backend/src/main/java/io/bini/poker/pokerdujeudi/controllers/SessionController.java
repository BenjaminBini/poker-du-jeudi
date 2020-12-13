package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.dto.CreateSessionDTO;
import io.bini.poker.pokerdujeudi.model.*;
import io.bini.poker.pokerdujeudi.service.place.PlaceService;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import io.bini.poker.pokerdujeudi.service.season.SeasonService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sessions")
public class SessionController {
    private final SeasonService seasonService;
    private final SessionService sessionService;
    private final PlayerService playerService;
    private final PlaceService placeService;
    private final PlayerResultService playerResultService;

    @Autowired
    public SessionController(SeasonService seasonService, SessionService sessionService, PlayerService playerService, PlaceService placeService, PlayerResultService playerResultService) {
        this.seasonService = seasonService;
        this.sessionService = sessionService;
        this.playerService = playerService;
        this.placeService = placeService;
        this.playerResultService = playerResultService;
    }

    @GetMapping("")
    public String sessions(Model model) {
        List<Season> seasons = this.seasonService.list();
        seasons.sort(Comparator.comparingInt(Season::getYear).reversed());

        List<Session> sessions = this.sessionService.list();
        Map<Long, List<Session>> sessionsBySeason = sessions.stream()
                .collect(Collectors.groupingBy(s -> s.getSeason().getSeasonId()));

        model.addAttribute("sessions", sessionsBySeason);
        model.addAttribute("seasons", seasons);
        model.addAttribute("active", "sessions");
        return "sessions";
    }

    @GetMapping("{sessionId}")
    public String session(Model model, @PathVariable long sessionId) {
        Session session = this.sessionService.get(sessionId);
        model.addAttribute("session", session);
        model.addAttribute("active", "sessions");
        Session nextSession = this.sessionService.getNextSession(sessionId);
        Session previousSession = this.sessionService.getPreviousSession(sessionId);
        model.addAttribute("nextSession", nextSession);
        model.addAttribute("previousSession", previousSession);
        return "session";
    }

    @GetMapping("add")
    public String addSession(Model model) {
        List<Player> players = this.playerService.list();
        model.addAttribute("players", players);

        List<Place> places = this.placeService.list();
        model.addAttribute("places", places);

        List<Season> seasons = this.seasonService.list();
        model.addAttribute("seasons", seasons);
        model.addAttribute("active", "sessions");
        return "add-session";
    }

    @PostMapping("add")
    public String addSession(Model model, @ModelAttribute CreateSessionDTO createSessionDTO) {
        Session session = new Session();
        List<PlayerResult> playerResults = new ArrayList<>();
        for (long playerId : createSessionDTO.getPlayerIds()) {
            Optional<Player> player = this.playerService.get(playerId);
            if (player.isPresent()) {
                PlayerResult playerResult = new PlayerResult();
                playerResult.setPlayer(player.get());
                playerResult.setSession(session);
                playerResults.add(playerResult);
            }
        }

        session.setPlayerResults(playerResults);
        session.setDate(createSessionDTO.getDate());
        Optional<Place> place = this.placeService.get(createSessionDTO.getPlaceId());
        place.ifPresent(session::setPlace);

        Optional<Season> season = this.seasonService.get(createSessionDTO.getSeasonId());
        season.ifPresent(session::setSeason);
        this.sessionService.save(session);
        playerResults.forEach(playerResultService::save);
        return "redirect:/sessions/" + session.getSessionId();
    }

    @GetMapping("delete/{sessionId}")
    public String deletePlayer(@PathVariable Long sessionId) {
        this.sessionService.delete(sessionId);
        return "redirect:/sessions";
    }

}
