package com.example.TickNet.repository;

import com.example.TickNet.entity.Reservation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByVisiteurId(Long visiteurId);
    List<Reservation> findBySessionId(Long sessionId);

    // Total places déjà réservées pour une session (statut != 'CANCELLED')
    @Query("""
     select coalesce(sum(r.nbPlaces), 0)
     from Reservation r
     where r.session.id = :sessionId
       and (r.statut is null or r.statut <> 'CANCELLED')
  """)
    long sumReservedSeats(@Param("sessionId") Long sessionId);
}
