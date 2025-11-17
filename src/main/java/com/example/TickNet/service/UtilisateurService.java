package com.example.TickNet.service;

import com.example.TickNet.entity.Utilisateur;
import com.example.TickNet.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        // In a real application, you would encrypt the password here
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
}
