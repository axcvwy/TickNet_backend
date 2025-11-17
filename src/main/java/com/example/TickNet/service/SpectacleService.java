package com.example.TickNet.service;

import com.example.TickNet.entity.Spectacle;
import com.example.TickNet.repository.SpectacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpectacleService {

    @Autowired
    private SpectacleRepository spectacleRepository;

    public List<Spectacle> getAllSpectacles() {
        return spectacleRepository.findAll();
    }

    public Optional<Spectacle> getSpectacleById(Long id) {
        return spectacleRepository.findById(id);
    }

    public Spectacle saveSpectacle(Spectacle spectacle) {
        return spectacleRepository.save(spectacle);
    }

    public void deleteSpectacle(Long id) {
        spectacleRepository.deleteById(id);
    }
}
