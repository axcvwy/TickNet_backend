package com.example.TickNet.repository;

import com.example.TickNet.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUtilisateurId(Long userId);
    List<Reservation> findBySessionId(Long sessionId);
}
