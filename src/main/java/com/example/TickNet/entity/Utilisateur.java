package com.example.TickNet.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
}
