package com.example.TickNet.repository;

import com.example.TickNet.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Long> {
    List<Salle> findByNomIgnoreCase(String nom);
}
