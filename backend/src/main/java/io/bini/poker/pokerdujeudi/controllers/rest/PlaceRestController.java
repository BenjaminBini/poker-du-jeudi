package io.bini.poker.pokerdujeudi.controllers.rest;

import io.bini.poker.pokerdujeudi.model.Place;
import io.bini.poker.pokerdujeudi.service.place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceRestController {

    private final PlaceService placeService;

    @Autowired
    public PlaceRestController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping()
    public List<Place> getPlaces() {
        return this.placeService.list();
    }
}
