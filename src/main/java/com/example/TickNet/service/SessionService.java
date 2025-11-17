package com.example.TickNet.service;

import com.example.TickNet.entity.Session;
import com.example.TickNet.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Optional<Session> getSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public List<Session> getSessionsBySpectacleId(Long spectacleId) {
        return sessionRepository.findBySpectacleId(spectacleId);
    }

    public void decreaseAvailableSeats(Long sessionId, int numberOfSeats) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            if (session.getAvailableSeats() >= numberOfSeats) {
                session.setAvailableSeats(session.getAvailableSeats() - numberOfSeats);
                sessionRepository.save(session);
            } else {
                throw new RuntimeException("Not enough available seats for session: " + sessionId);
            }
        } else {
            throw new RuntimeException("Session not found with ID: " + sessionId);
        }
    }

    public void increaseAvailableSeats(Long sessionId, int numberOfSeats) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            session.setAvailableSeats(session.getAvailableSeats() + numberOfSeats);
            sessionRepository.save(session);
        } else {
            throw new RuntimeException("Session not found with ID: " + sessionId);
        }
    }
}
