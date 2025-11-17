package com.example.TickNet.service;

import com.example.TickNet.entity.Reservation;
import com.example.TickNet.entity.Session;
import com.example.TickNet.entity.Utilisateur;
import com.example.TickNet.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UtilisateurService utilisateurService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(Long userId, Long sessionId, int nbPlaces) {
        Optional<Utilisateur> utilisateurOptional = utilisateurService.getUtilisateurById(userId);
        Optional<Session> sessionOptional = sessionService.getSessionById(sessionId);

        if (utilisateurOptional.isPresent() && sessionOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            Session session = sessionOptional.get();

            if (session.getAvailableSeats() < nbPlaces) {
                throw new RuntimeException("Not enough available seats for this session.");
            }

            Reservation reservation = new Reservation();
            reservation.setUtilisateur(utilisateur);
            reservation.setSession(session);
            reservation.setNbPlaces(nbPlaces);
            reservation.setMontantTotal(nbPlaces * session.getPrice());
            reservation.setDate(LocalDate.now());

            sessionService.decreaseAvailableSeats(sessionId, nbPlaces);
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("User or Session not found");
        }
    }

    public void deleteReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            sessionService.increaseAvailableSeats(reservation.getSession().getId(), reservation.getNbPlaces());
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found with ID: " + id);
        }
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUtilisateurId(userId);
    }

    public List<Reservation> getReservationsBySessionId(Long sessionId) {
        return reservationRepository.findBySessionId(sessionId);
    }

    public double getTotalSales() {
        return reservationRepository.findAll().stream()
                .mapToDouble(Reservation::getMontantTotal)
                .sum();
    }

    public long getTotalReservations() {
        return reservationRepository.count();
    }

    public double getTotalSalesForSpectacle(Long spectacleId) {
        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getSession().getSpectacle().getId().equals(spectacleId))
                .mapToDouble(Reservation::getMontantTotal)
                .sum();
    }

    public long getTotalReservationsForSpectacle(Long spectacleId) {
        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getSession().getSpectacle().getId().equals(spectacleId))
                .count();
    }
}
