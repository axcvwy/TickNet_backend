package com.example.TickNet.controller;

import com.example.TickNet.service.ReservationService;
import com.example.TickNet.service.SpectacleService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ReservationService reservationService;
    private final SpectacleService spectacleService;

    public AdminController(ReservationService reservationService, SpectacleService spectacleService) {
        this.reservationService = reservationService;
        this.spectacleService = spectacleService;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        return Map.of(
                "totalSales", reservationService.getTotalSales(),
                "totalReservations", reservationService.getTotalReservations(),
                "spectacles", spectacleService.getAllSpectacles()
        );
    }
}
