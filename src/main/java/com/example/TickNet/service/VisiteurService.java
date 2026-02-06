package com.example.TickNet.service;

import com.example.TickNet.Dto.RegisterRequest;
import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.repository.VisiteurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VisiteurService {

    private final VisiteurRepository visiteurRepository;
    private final PasswordEncoder passwordEncoder;

    // la liste des emails admin
    private static final Set<String> ADMIN_EMAILS = Set.of(
            "admin@ticknet.com",
            "superadmin@test.com",
            "manager@ticknet.com"
    );

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

    public List<Visiteur> getAllVisiteurs() {
        List<Visiteur> users = visiteurRepository.findAll();
        users.forEach(this::assignRole);
        return users;
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

        Visiteur saved = visiteurRepository.save(u);
        assignRole(saved);
        return saved;
    }

    public Visiteur login(String email, String motDePasse) {
        Visiteur u = visiteurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Identifiants invalides"));

        if (!passwordEncoder.matches(motDePasse, u.getMotDePasse())) {
            throw new RuntimeException("Identifiants invalides");
        }

        assignRole(u);
        return u;
    }

    // activation / desactivation du compte
    public Visiteur toggleStatus(Long id) {
        Visiteur u = visiteurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Ensure 'actif' is not null before flipping
        boolean currentStatus = u.getActif() != null ? u.getActif() : false;
        u.setActif(!currentStatus);

        Visiteur saved = visiteurRepository.save(u);
        assignRole(saved);
        return saved;
    }


    // suppresion du compte
    public void deleteUser(Long id) {
        if (!visiteurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        visiteurRepository.deleteById(id);
    }

    // Checks email et role
    private void assignRole(Visiteur u) {
        if (ADMIN_EMAILS.contains(u.getEmail())) {
            u.setRole("ADMIN");
        } else {
            u.setRole("CLIENT");
        }
    }
}
