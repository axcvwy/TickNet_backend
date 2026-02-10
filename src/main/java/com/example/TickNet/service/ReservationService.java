package com.example.TickNet.service;

import com.example.TickNet.Dto.ReservationCreateRequest;
import com.example.TickNet.entity.Reservation;
import com.example.TickNet.entity.Session;
import com.example.TickNet.entity.Visiteur;
import com.example.TickNet.repository.ReservationRepository;
import com.example.TickNet.repository.SessionRepository;
import com.example.TickNet.repository.VisiteurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SessionRepository sessionRepository;
    private final VisiteurRepository visiteurRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              SessionRepository sessionRepository,
                              VisiteurRepository visiteurRepository) {
        this.reservationRepository = reservationRepository;
        this.sessionRepository = sessionRepository;
        this.visiteurRepository = visiteurRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByVisiteur(Long visiteurId) {
        return reservationRepository.findByVisiteurId(visiteurId);
    }

    /** ✅ TOTAL des ventes */
    public BigDecimal getTotalSales() {
        return reservationRepository.findAll().stream()
                .map(Reservation::getMontantTotal)
                .filter(mt -> mt != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** ✅ TOTAL des réservations */
    public long getTotalReservations() {
        return reservationRepository.count();
    }

    @Transactional
    public Reservation createReservation(ReservationCreateRequest req) {

        Visiteur visiteur = visiteurRepository.findById(req.visiteurId())
                .orElseThrow(() -> new RuntimeException("Visiteur introuvable"));

        Session session = sessionRepository.findById(req.sessionId())
                .orElseThrow(() -> new RuntimeException("Session introuvable"));

        long alreadyReserved = reservationRepository.sumReservedSeats(session.getId());
        long capacity = session.getCapacite() != null ? session.getCapacite() : 0L;
        long remaining = capacity - alreadyReserved;

        if (remaining < req.nbPlaces()) {
            throw new RuntimeException("Pas assez de places disponibles. Restant: " + remaining);
        }

        // ✅ prix BigDecimal
        BigDecimal prix = session.getPrix() != null ? session.getPrix() : BigDecimal.ZERO;

        // ✅ total BigDecimal
        BigDecimal total = prix.multiply(BigDecimal.valueOf(req.nbPlaces()));

        Reservation r = new Reservation();
        r.setVisiteur(visiteur);
        r.setSession(session);
        r.setNbPlaces(req.nbPlaces());
        r.setMontantTotal(total);          // ✅ BigDecimal
        r.setStatut("CONFIRMED");
        r.setDateReservation(LocalDateTime.now());

        return reservationRepository.save(r);
    }

    public void cancelReservation(Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation introuvable"));
        r.setStatut("CANCELLED");
        reservationRepository.save(r);
    }
}
