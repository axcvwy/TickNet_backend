package com.example.TickNet.controller;

import com.example.TickNet.entity.Spectacle;
import com.example.TickNet.service.SpectacleService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spectacles")
public class SpectacleController {

    private final SpectacleService spectacleService;

    public SpectacleController(SpectacleService spectacleService) {
        this.spectacleService = spectacleService;
    }

    @GetMapping
    public List<Spectacle> all() {
        return spectacleService.getAllSpectacles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spectacle> one(@PathVariable Long id) {
        return spectacleService.getSpectacleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Spectacle> create(@RequestBody Spectacle spectacle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(spectacleService.saveSpectacle(spectacle));
    }

    @PutMapping("/{id}")
    public Spectacle update(@PathVariable Long id, @RequestBody Spectacle spectacle) {
        return spectacleService.updateSpectacle(id, spectacle);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        spectacleService.deleteSpectacle(id);
    }
}
