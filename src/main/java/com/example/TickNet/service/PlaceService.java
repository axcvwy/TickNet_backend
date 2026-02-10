package com.example.TickNet.service;

import com.example.TickNet.Dto.PlaceDto;
import com.example.TickNet.entity.Place;
import com.example.TickNet.entity.Session;
import com.example.TickNet.repository.PlaceRepository;
import com.example.TickNet.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final SessionRepository sessionRepository;

    public PlaceService(PlaceRepository placeRepository, SessionRepository sessionRepository) {
        this.placeRepository = placeRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<PlaceDto> getPlacesBySalle(Long salleId) {
        return placeRepository.findBySalleId(salleId).stream()
                .map(p -> new PlaceDto(p.getId(), p.getRangee(), p.getNumero(), p.getTypePlace(), Boolean.TRUE.equals(p.getActive())))
                .toList();
    }

    // Ici on renvoie les places de la salle associée à la session
    public List<PlaceDto> getPlacesBySession(Long sessionId) {
        Session s = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session introuvable"));
        return getPlacesBySalle(s.getSalle().getId());
    }
}
