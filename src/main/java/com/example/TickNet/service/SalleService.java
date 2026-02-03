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

    public Optional<Salle> getSalleById(Long id) {
        return salleRepository.findById(id);
    }

    public Salle createSalle(Salle salle) {
        // Si ta DB gère déjà l'id auto, ne pas le forcer
        return salleRepository.save(salle);
    }

    public Salle updateSalle(Long id, Salle updated) {
        return salleRepository.findById(id)
                .map(existing -> {
                    existing.setNom(updated.getNom());
                    existing.setCapacite(updated.getCapacite());
                    existing.setLocalisation(updated.getLocalisation());
                    return salleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Salle non trouvée avec id " + id));
    }

    public void deleteSalle(Long id) {
        if (!salleRepository.existsById(id)) {
            throw new RuntimeException("Salle non trouvée avec id " + id);
        }
        salleRepository.deleteById(id);
    }
}
