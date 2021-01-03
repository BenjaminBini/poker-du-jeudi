package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.model.Season;
import io.bini.poker.pokerdujeudi.service.season.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
public class SeasonRestController {
    private final SeasonService seasonService;

    @Autowired
    public SeasonRestController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping
    public List<Season> getSeasons() {
        return this.seasonService.list();
    }
}
