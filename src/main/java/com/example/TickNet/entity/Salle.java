package com.example.TickNet.entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salle_id")
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "capacite")
    private Integer capacite;

    // Toutes les places de cette salle
    @OneToMany(mappedBy = "salle")
    private List<Place> places;

    // Toutes les sessions (représentations) dans cette salle
    @OneToMany(mappedBy = "salle")
    private List<Session> sessions;

    public Salle() {
    }

    public Salle(Long id, String nom, Integer capacite) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
