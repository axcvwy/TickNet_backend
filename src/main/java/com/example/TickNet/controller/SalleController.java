package com.example.TickNet.controller;

import com.example.TickNet.entity.Salle;
import com.example.TickNet.service.SalleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
@CrossOrigin(origins = "http://localhost:5174")
public class SalleController {
    private final SalleService salleService;

    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    /**
     * GET /api/salles
     */
    @GetMapping
    public List<Salle> getAllSalles() {
        return salleService.getAllSalles();
    }

    /**
     * GET /api/salles/{id}
     */
    @GetMapping("/{id}")
    public Salle getSalleById(@PathVariable Integer id) {
        return salleService.getSalleById(id)
                .orElseThrow(() -> new RuntimeException("Salle non trouvée avec id " + id));
    }

    /**
     * POST /api/salles
     */
    @PostMapping
    public Salle createSalle(@RequestBody Salle salle) {
        return salleService.createSalle(salle);
    }

    /**
     * PUT /api/salles/{id}
     */
    @PutMapping("/{id}")
    public Salle updateSalle(@PathVariable Integer id, @RequestBody Salle salle) {
        return salleService.updateSalle(id, salle);
    }

    /**
     * DELETE /api/salles/{id}
     */
    @DeleteMapping("/{id}")
    public void deleteSalle(@PathVariable Integer id) {
        salleService.deleteSalle(id);
    }
}
