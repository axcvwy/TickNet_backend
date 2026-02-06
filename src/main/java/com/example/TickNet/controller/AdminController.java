package com.example.TickNet.controller;

import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.service.ReservationService;
import com.example.TickNet.service.SpectacleService;
import com.example.TickNet.service.VisiteurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ReservationService reservationService;
    private final SpectacleService spectacleService;
    private final VisiteurService visiteurService;

    public AdminController(ReservationService reservationService,
                           SpectacleService spectacleService,
                           VisiteurService visiteurService) {
        this.reservationService = reservationService;
        this.spectacleService = spectacleService;
        this.visiteurService = visiteurService;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        return Map.of(
                "totalSales", reservationService.getTotalSales(),
                "totalReservations", reservationService.getTotalReservations(),
                "spectacles", spectacleService.getAllSpectacles()
        );
    }

    @GetMapping("/users")
    public List<Visiteur> getAllUsers() {
        return visiteurService.getAllVisiteurs();
    }

    @PatchMapping("/users/{id}/toggle-status")
    public ResponseEntity<Visiteur> toggleUserStatus(@PathVariable Long id) {
        return ResponseEntity.ok(visiteurService.toggleStatus(id));
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        visiteurService.deleteUser(id);
    }
}
