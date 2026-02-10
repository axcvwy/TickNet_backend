package com.example.TickNet.controller;

import com.example.TickNet.Dto.AuthResponse;
import com.example.TickNet.config.JwtUtil;
import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.repository.VisiteurRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class OAuthController {
    private final VisiteurRepository visiteurRepository;
    private final JwtUtil jwtUtil;

    public OAuthController(VisiteurRepository visiteurRepository, JwtUtil jwtUtil) {
        this.visiteurRepository = visiteurRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/oauth2/google")
    public AuthResponse googleOAuth(OAuth2AuthenticationToken authentication) {
        String email = authentication.getPrincipal().getAttribute("email");
        Visiteur user = visiteurRepository.findByEmail(email)
            .orElseGet(() -> {
                Visiteur newUser = new Visiteur();
                newUser.setEmail(email);
                newUser.setNom(authentication.getPrincipal().getAttribute("name"));
                newUser.setActif(true);
                return visiteurRepository.save(newUser);
            });
        String token = jwtUtil.generateToken(email);
        return new AuthResponse(token, email, user.getNom(), user.getRole());
    }
}
