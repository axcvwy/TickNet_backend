package com.example.TickNet.controller;

import com.example.TickNet.entity.Place;
import com.example.TickNet.service.PlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "http://localhost:5174")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * Récupérer toutes les places d'une session (représentation)
     * GET /api/places/session/1
     */
    @GetMapping("/session/{sessionId}")
    public List<Place> getPlacesBySession(@PathVariable Integer sessionId) {
        return placeService.getPlacesBySession(sessionId);
    }

    /**
     * Récupérer toutes les places d'une salle
     * GET /api/places/salle/1
     */
    @GetMapping("/salle/{salleId}")
    public List<Place> getPlacesBySalle(@PathVariable Integer salleId) {
        return placeService.getPlacesBySalle(salleId);
    }

    /**
     * Réserver une liste de places pour une session
     * POST /api/places/session/1/reserver
     * Body JSON : [1,2,3]
     */
    @PostMapping("/session/{sessionId}/reserver")
    public List<Place> reserverPlaces(
            @PathVariable Integer sessionId,
            @RequestBody List<Integer> placeIds
    ) {
        return placeService.reserverPlaces(sessionId, placeIds);
    }
}
