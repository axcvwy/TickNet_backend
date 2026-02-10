package com.example.TickNet.controller;

import com.example.TickNet.entity.Salle;
import com.example.TickNet.service.SalleService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
public class SalleController {

    private final SalleService salleService;

    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping
    public List<Salle> getAllSalles() {
        return salleService.getAllSalles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleById(@PathVariable Long id) {
        return salleService.getSalleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Salle> createSalle(@RequestBody Salle salle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salleService.createSalle(salle));
    }

    @PutMapping("/{id}")
    public Salle updateSalle(@PathVariable Long id, @RequestBody Salle salle) {
        return salleService.updateSalle(id, salle);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSalle(@PathVariable Long id) {
        salleService.deleteSalle(id);
    }
}
