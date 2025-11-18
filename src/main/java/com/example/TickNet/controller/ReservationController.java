package com.example.TickNet.controller;

import com.example.TickNet.entity.Reservation;
import com.example.TickNet.entity.Spectacle;
import com.example.TickNet.entity.Utilisateur;
import com.example.TickNet.service.ReservationService;
import com.example.TickNet.service.SpectacleService;
import com.example.TickNet.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
@CrossOrigin(origins = "http://localhost:5174")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SpectacleService spectacleService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/book/{spectacleId}")
    public String showBookingForm(@PathVariable Long spectacleId, Model model) {
        Spectacle spectacle = spectacleService.getSpectacleById(spectacleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid spectacle Id:" + spectacleId));
        model.addAttribute("spectacle", spectacle);
        model.addAttribute("reservation", new Reservation());
        return "reservation/book"; // Assuming a Thymeleaf template
    }

    @PostMapping("/book")
    public String bookReservation(@RequestParam Long spectacleId, @RequestParam int nbPlaces, HttpSession session) {
        // For now, assuming a user is logged in and their ID is in the session
        // In a real app, Spring Security would handle authenticated user retrieval
        Long userId = (Long) session.getAttribute("userId"); // Placeholder for logged-in user

        if (userId == null) {
            return "redirect:/users/login"; // Redirect to login if no user in session
        }

        reservationService.createReservation(userId, spectacleId, nbPlaces);
        return "redirect:/reservations/my";
    }

    @GetMapping("/my")
    public String myReservations(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId"); // Placeholder for logged-in user

        if (userId == null) {
            return "redirect:/users/login";
        }

        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        model.addAttribute("reservations", reservations);
        return "reservation/my"; // Assuming a Thymeleaf template
    }

    // Admin functionalities
    @GetMapping("/admin")
    public String listAllReservations(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "reservation/admin-list"; // Assuming a Thymeleaf template
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations/admin";
    }
}
