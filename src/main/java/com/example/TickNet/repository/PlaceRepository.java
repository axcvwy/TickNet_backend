package com.example.TickNet.repository;

import com.example.TickNet.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    // toutes les places d'une session
    List<Place> findBySessionId(Integer sessionId);

    // toutes les places d'une salle
    List<Place> findBySalleId(Integer salleId);

    // marquer une place comme réservée
    @Transactional
    @Modifying
    @Query("UPDATE Place p SET p.estReservee = true WHERE p.id = :placeId")
    void reserverPlace(@Param("placeId") Integer placeId);
}
