package com.example.TickNet.repository;

import com.example.TickNet.entity.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
    Optional<Visiteur> findByEmail(String email);

    boolean existsByEmail(String email);
}
