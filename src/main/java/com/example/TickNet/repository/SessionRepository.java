package com.example.TickNet.repository;

import com.example.TickNet.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findBySpectacleId(Long spectacleId);
}
