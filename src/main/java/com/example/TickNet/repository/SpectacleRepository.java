package com.example.TickNet.repository;

import com.example.TickNet.entity.Spectacle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectacleRepository extends JpaRepository<Spectacle, Long> {
}
