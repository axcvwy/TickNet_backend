package com.example.TickNet.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private int capacity;
    private int availableSeats;
    private double price; // Price for this specific session, can override Spectacle's default

    @ManyToOne
    @JoinColumn(name = "spectacle_id")
    private Spectacle spectacle;
}
