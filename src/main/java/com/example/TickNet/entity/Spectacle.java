package com.example.TickNet.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Spectacle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String image;
    private double prix;
    private LocalDate date;
    private String genre;

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Session> sessions;
}
