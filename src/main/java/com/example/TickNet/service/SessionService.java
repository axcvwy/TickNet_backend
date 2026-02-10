package com.example.TickNet.service;

import com.example.TickNet.entity.Salle;
import com.example.TickNet.entity.Session;
import com.example.TickNet.entity.Spectacle;
import com.example.TickNet.repository.SalleRepository;
import com.example.TickNet.repository.SessionRepository;
import com.example.TickNet.repository.SpectacleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SpectacleRepository spectacleRepository;
    private final SalleRepository salleRepository;

    public SessionService(SessionRepository sessionRepository,
                          SpectacleRepository spectacleRepository,
                          SalleRepository salleRepository) {
        this.sessionRepository = sessionRepository;
        this.spectacleRepository = spectacleRepository;
        this.salleRepository = salleRepository;
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Optional<Session> getSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    public List<Session> getSessionsBySpectacleId(Long spectacleId) {
        return sessionRepository.findBySpectacleId(spectacleId);
    }

    public Session saveSession(Session session) {
        // spectacle obligatoire
        if (session.getSpectacle() == null || session.getSpectacle().getId() == null) {
            throw new RuntimeException("spectacle_id est obligatoire");
        }
        // salle obligatoire
        if (session.getSalle() == null || session.getSalle().getId() == null) {
            throw new RuntimeException("salle_id est obligatoire");
        }

        Spectacle spectacle = spectacleRepository.findById(session.getSpectacle().getId())
                .orElseThrow(() -> new RuntimeException("Spectacle introuvable"));

        Salle salle = salleRepository.findById(session.getSalle().getId())
                .orElseThrow(() -> new RuntimeException("Salle introuvable"));

        session.setSpectacle(spectacle);
        session.setSalle(salle);

        // Optionnel : si capacité non fournie, on met celle de la salle
        if (session.getCapacite() == null && salle.getCapacite() != null) {
            session.setCapacite(salle.getCapacite());
        }

        return sessionRepository.save(session);
    }

    public Session updateSession(Long id, Session updated) {
        return sessionRepository.findById(id)
                .map(existing -> {

                    if (updated.getSpectacle() != null && updated.getSpectacle().getId() != null) {
                        Spectacle sp = spectacleRepository.findById(updated.getSpectacle().getId())
                                .orElseThrow(() -> new RuntimeException("Spectacle introuvable"));
                        existing.setSpectacle(sp);
                    }

                    if (updated.getSalle() != null && updated.getSalle().getId() != null) {
                        Salle sa = salleRepository.findById(updated.getSalle().getId())
                                .orElseThrow(() -> new RuntimeException("Salle introuvable"));
                        existing.setSalle(sa);

                        if (updated.getCapacite() == null && sa.getCapacite() != null) {
                            existing.setCapacite(sa.getCapacite());
                        }
                    }

                    if (updated.getDateHeure() != null) existing.setDateHeure(updated.getDateHeure());
                    if (updated.getPrix() != null) existing.setPrix(updated.getPrix());
                    if (updated.getCapacite() != null) existing.setCapacite(updated.getCapacite());

                    return sessionRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Session non trouvée avec id " + id));
    }

    public void deleteSession(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new RuntimeException("Session non trouvée avec id " + id);
        }
        sessionRepository.deleteById(id);
    }
}
