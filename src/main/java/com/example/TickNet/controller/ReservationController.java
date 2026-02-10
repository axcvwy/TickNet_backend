package com.example.TickNet.controller;

import com.example.TickNet.Dto.ReservationCreateRequest;
import com.example.TickNet.entity.Reservation;
import com.example.TickNet.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> listAll() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@Valid @RequestBody ReservationCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(req));
    }

    @GetMapping("/visiteur/{visiteurId}")
    public List<Reservation> byVisiteur(@PathVariable Long visiteurId) {
        return reservationService.getReservationsByVisiteur(visiteurId);
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }
}
