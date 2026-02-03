package com.example.TickNet.repository;

import com.example.TickNet.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findBySpectacleId(Long spectacleId);
}
