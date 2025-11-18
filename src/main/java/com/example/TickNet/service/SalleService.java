package com.example.TickNet.service;

import com.example.TickNet.entity.Salle;
import com.example.TickNet.repository.SalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalleService {
    private final SalleRepository salleRepository;

    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    public Optional<Salle> getSalleById(Integer id) {
        return salleRepository.findById(id);
    }

    public Salle createSalle(Salle salle) {
        return salleRepository.save(salle);
    }

    public Salle updateSalle(Integer id, Salle updated) {
        return salleRepository.findById(id)
                .map(existing -> {
                    existing.setNom(updated.getNom());
                    existing.setCapacite(updated.getCapacite());
                    return salleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Salle non trouvée avec id " + id));
    }

    public void deleteSalle(Integer id) {
        salleRepository.deleteById(id);
    }
}
