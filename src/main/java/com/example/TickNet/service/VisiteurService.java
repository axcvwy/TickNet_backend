package com.example.TickNet.service;

import com.example.TickNet.Dto.RegisterRequest;
import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.repository.VisiteurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VisiteurService {

    private final VisiteurRepository visiteurRepository;
    private final PasswordEncoder passwordEncoder;

    public VisiteurService(VisiteurRepository visiteurRepository, PasswordEncoder passwordEncoder) {
        this.visiteurRepository = visiteurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Visiteur> getById(Long id) {
        return visiteurRepository.findById(id);
    }

    public Optional<Visiteur> findByEmail(String email) {
        return visiteurRepository.findByEmail(email);
    }

    public Visiteur register(RegisterRequest req) {
        visiteurRepository.findByEmail(req.email()).ifPresent(u -> {
            throw new RuntimeException("Email déjà utilisé");
        });

        Visiteur u = new Visiteur();
        u.setNom(req.nom());
        u.setEmail(req.email());
        u.setTelephone(req.telephone());
        u.setMotDePasse(passwordEncoder.encode(req.motDePasse()));
        u.setActif(true);
        u.setDateCreation(LocalDateTime.now());

        return visiteurRepository.save(u);
    }

    public Visiteur login(String email, String motDePasse) {
        Visiteur u = visiteurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Identifiants invalides"));

        if (!passwordEncoder.matches(motDePasse, u.getMotDePasse())) {
            throw new RuntimeException("Identifiants invalides");
        }
        return u;
    }
}
