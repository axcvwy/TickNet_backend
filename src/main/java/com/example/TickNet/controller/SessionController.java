package com.example.TickNet.controller;

import com.example.TickNet.entity.Session;
import com.example.TickNet.service.SessionService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<Session> listAll() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/spectacle/{spectacleId}")
    public List<Session> listBySpectacle(@PathVariable Long spectacleId) {
        return sessionService.getSessionsBySpectacleId(spectacleId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getOne(@PathVariable Long id) {
        return sessionService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Session> create(@RequestBody Session session) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.saveSession(session));
    }

    @PutMapping("/{id}")
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        return sessionService.updateSession(id, session);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sessionService.deleteSession(id);
    }
}
