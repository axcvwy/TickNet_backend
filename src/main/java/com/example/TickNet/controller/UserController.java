package com.example.TickNet.controller;

import com.example.TickNet.Dto.UpdatePasswordDTO;
import com.example.TickNet.Dto.UpdateProfileDTO;
import com.example.TickNet.Dto.UserProfileDTO;
import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.repository.VisiteurRepository;
import com.example.TickNet.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final VisiteurRepository visiteurRepository;

    @GetMapping
    public ResponseEntity<UserProfileDTO> getProfile(Authentication authentication) {
        String email = authentication.getName();
        Visiteur visiteur = visiteurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(userService.getUserProfile(visiteur.getId()));
    }

    @PutMapping
    public ResponseEntity<UserProfileDTO> updateProfile(@Valid @RequestBody UpdateProfileDTO updateDto,
            Authentication authentication) {
        String email = authentication.getName();
        Visiteur visiteur = visiteurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(userService.updateProfile(visiteur.getId(), updateDto));
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordDTO passwordDto,
            Authentication authentication) {
        String email = authentication.getName();
        Visiteur visiteur = visiteurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.updatePassword(visiteur.getId(), passwordDto);
        return ResponseEntity.ok().build();
    }
}
