package com.example.TickNet.controller;

import com.example.TickNet.config.JwtUtil;
import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.repository.VisiteurRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/users/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final VisiteurRepository visiteurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequest req) {
        if (visiteurRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        Visiteur visiteur = new Visiteur();
        visiteur.setNom(req.getName());
        visiteur.setEmail(req.getEmail());
        visiteur.setMotDePasse(passwordEncoder.encode(req.getPassword()));
        visiteur.setTelephone(req.getPhone());
        visiteur.setDateCreation(LocalDateTime.now());
        visiteur.setActif(true);

        visiteurRepository.save(visiteur);

        String token = jwtUtil.generateToken(visiteur.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserRequest req) {
        Visiteur visiteur = visiteurRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), visiteur.getMotDePasse())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(visiteur.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @Data
    public static class RegisterUserRequest {
        @NotBlank
        private String name;
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
        private String phone;
    }

    @Data
    public static class LoginUserRequest {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
    }
}
