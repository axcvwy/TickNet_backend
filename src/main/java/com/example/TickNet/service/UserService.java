package com.example.TickNet.service;

import com.example.TickNet.Dto.UpdatePasswordDTO;
import com.example.TickNet.Dto.UpdateProfileDTO;
import com.example.TickNet.Dto.UserProfileDTO;
import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.repository.VisiteurRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final VisiteurRepository visiteurRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileDTO getUserProfile(Long userId) {
        Visiteur visiteur = visiteurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(visiteur.getId());
        dto.setName(visiteur.getNom());
        dto.setEmail(visiteur.getEmail());
        dto.setPhone(visiteur.getTelephone());
        dto.setCreatedAt(visiteur.getDateCreation());
        return dto;
    }

    @Transactional
    public UserProfileDTO updateProfile(Long userId, UpdateProfileDTO updateDto) {
        Visiteur visiteur = visiteurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        visiteur.setNom(updateDto.getName());
        visiteur.setTelephone(updateDto.getPhone());

        Visiteur savedVisiteur = visiteurRepository.save(visiteur);

        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(savedVisiteur.getId());
        dto.setName(savedVisiteur.getNom());
        dto.setEmail(savedVisiteur.getEmail());
        dto.setPhone(savedVisiteur.getTelephone());
        dto.setCreatedAt(savedVisiteur.getDateCreation());
        return dto;
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordDTO passwordDto) {
        Visiteur visiteur = visiteurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(passwordDto.getCurrentPassword(), visiteur.getMotDePasse())) {
            throw new RuntimeException("Invalid current password");
        }

        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
            throw new RuntimeException("New passwords do not match");
        }

        visiteur.setMotDePasse(passwordEncoder.encode(passwordDto.getNewPassword()));
        visiteurRepository.save(visiteur);
    }
}
