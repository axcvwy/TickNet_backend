package com.example.TickNet.service;

import com.example.TickNet.entity.Place;
import com.example.TickNet.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    /**
     * Récupérer toutes les places d'une session (représentation)
     */
    public List<Place> getPlacesBySession(Integer sessionId) {
        return placeRepository.findBySessionId(sessionId);
    }

    /**
     * Récupérer toutes les places d'une salle
     */
    public List<Place> getPlacesBySalle(Integer salleId) {
        return placeRepository.findBySalleId(salleId);
    }

    /**
     * Réserver une liste de places pour une session,
     * puis renvoyer la liste actualisée des places de cette session.
     */
    public List<Place> reserverPlaces(Integer sessionId, List<Integer> placeIds) {
        if (placeIds != null) {
            for (Integer placeId : placeIds) {
                placeRepository.reserverPlace(placeId);
            }
        }
        return getPlacesBySession(sessionId);
    }
}
