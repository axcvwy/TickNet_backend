package com.example.TickNet.service;

import com.example.TickNet.entity.Spectacle;
import com.example.TickNet.repository.SpectacleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpectacleService {

    private final SpectacleRepository spectacleRepository;

    public SpectacleService(SpectacleRepository spectacleRepository) {
        this.spectacleRepository = spectacleRepository;
    }

    public List<Spectacle> getAllSpectacles() {
        return spectacleRepository.findAll();
    }

    public Optional<Spectacle> getSpectacleById(Long id) {
        return spectacleRepository.findById(id);
    }

    public Spectacle saveSpectacle(Spectacle spectacle) {
        return spectacleRepository.save(spectacle);
    }

    public Spectacle updateSpectacle(Long id, Spectacle updated) {
        return spectacleRepository.findById(id)
                .map(existing -> {
                    existing.setTitre(updated.getTitre());
                    existing.setDescription(updated.getDescription());
                    existing.setImageUrl(updated.getImageUrl());
                    existing.setGenre(updated.getGenre());
                    existing.setDureeMinutes(updated.getDureeMinutes());
                    return spectacleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Spectacle non trouvé avec id " + id));
    }

    public void deleteSpectacle(Long id) {
        if (!spectacleRepository.existsById(id)) {
            throw new RuntimeException("Spectacle non trouvé avec id " + id);
        }
        spectacleRepository.deleteById(id);
    }
}
