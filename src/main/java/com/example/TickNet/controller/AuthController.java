package com.example.TickNet.controller;

import com.example.TickNet.Dto.LoginRequest;
import com.example.TickNet.Dto.RegisterRequest;
import com.example.TickNet.Dto.AuthResponse;
import com.example.TickNet.service.VisiteurService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final VisiteurService visiteurService;

    public AuthController(VisiteurService visiteurService) {
        this.visiteurService = visiteurService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(visiteurService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(visiteurService.login(req.email(), req.motDePasse()));
    }
}
