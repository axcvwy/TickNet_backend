package com.example.TickNet.controller;

import com.example.TickNet.entity.Session;
import com.example.TickNet.entity.Spectacle;
import com.example.TickNet.service.SessionService;
import com.example.TickNet.service.SpectacleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private SpectacleService spectacleService;

    @GetMapping("/spectacle/{spectacleId}")
    public String listSessionsForSpectacle(@PathVariable Long spectacleId, Model model) {
        Spectacle spectacle = spectacleService.getSpectacleById(spectacleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid spectacle Id:" + spectacleId));
        List<Session> sessions = sessionService.getSessionsBySpectacleId(spectacleId);
        model.addAttribute("spectacle", spectacle);
        model.addAttribute("sessions", sessions);
        return "admin/session-list"; // Assuming a Thymeleaf template
    }

    @GetMapping("/new/{spectacleId}")
    public String showCreateSessionForm(@PathVariable Long spectacleId, Model model) {
        Spectacle spectacle = spectacleService.getSpectacleById(spectacleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid spectacle Id:" + spectacleId));
        Session session = new Session();
        session.setSpectacle(spectacle);
        model.addAttribute("session", session);
        return "admin/session-form"; // Assuming a Thymeleaf template
    }

    @PostMapping("/save")
    public String saveSession(@ModelAttribute Session session, @RequestParam Long spectacleId) {
        Spectacle spectacle = spectacleService.getSpectacleById(spectacleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid spectacle Id:" + spectacleId));
        session.setSpectacle(spectacle);
        session.setAvailableSeats(session.getCapacity()); // Initialize available seats
        sessionService.saveSession(session);
        return "redirect:/admin/sessions/spectacle/" + spectacleId;
    }

    @GetMapping("/edit/{id}")
    public String showEditSessionForm(@PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid session Id:" + id));
        model.addAttribute("session", session);
        return "admin/session-form"; // Assuming a Thymeleaf template
    }

    @GetMapping("/delete/{id}")
    public String deleteSession(@PathVariable Long id, @RequestParam Long spectacleId) {
        sessionService.deleteSession(id);
        return "redirect:/admin/sessions/spectacle/" + spectacleId;
    }
}
