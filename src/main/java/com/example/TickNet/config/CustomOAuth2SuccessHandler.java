package com.example.TickNet.config;

import com.example.TickNet.config.JwtUtil;
import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.repository.VisiteurRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final VisiteurRepository visiteurRepository;
    private final JwtUtil jwtUtil;

    public CustomOAuth2SuccessHandler(VisiteurRepository visiteurRepository, JwtUtil jwtUtil) {
        this.visiteurRepository = visiteurRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String email = oauthToken.getPrincipal().getAttribute("email");
        Visiteur user = visiteurRepository.findByEmail(email)
                .orElseGet(() -> {
                    Visiteur newUser = new Visiteur();
                    newUser.setEmail(email);
                    newUser.setNom(oauthToken.getPrincipal().getAttribute("name"));
                    newUser.setActif(true);
                    newUser.setDateCreation(java.time.LocalDateTime.now());
                    return newUser;
                });
        // Always set motDePasse for OAuth users
        user.setMotDePasse(user.getMotDePasse() != null ? user.getMotDePasse() : "oauth-google");
        // Always set dateCreation if null
        if (user.getDateCreation() == null) {
            user.setDateCreation(java.time.LocalDateTime.now());
        }
        visiteurRepository.save(user);
        String token = jwtUtil.generateTokenWithNom(email, user.getNom());
        String redirectUrl = "http://localhost:5174/oauth-success?token=" + token + "&nom=" + java.net.URLEncoder.encode(user.getNom(), "UTF-8");
        response.sendRedirect(redirectUrl);
    }
}
