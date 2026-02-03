package com.example.TickNet.controller;

import com.example.TickNet.Dto.PlaceDto;
import com.example.TickNet.service.PlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/salle/{salleId}")
    public List<PlaceDto> bySalle(@PathVariable Long salleId) {
        return placeService.getPlacesBySalle(salleId);
    }

    @GetMapping("/session/{sessionId}")
    public List<PlaceDto> bySession(@PathVariable Long sessionId) {
        return placeService.getPlacesBySession(sessionId);
    }
}
