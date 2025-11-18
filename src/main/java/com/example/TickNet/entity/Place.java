package com.example.TickNet.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seat") // adapte si ta table s'appelle autrement
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Integer id;

    @Column(name = "numero_place", nullable = false)
    private Integer numeroPlace;

    @Column(name = "est_reservee", nullable = false)
    private Boolean estReservee = false;

    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    public Place() {
    }

    public Place(Integer id, Integer numeroPlace, Boolean estReservee,
                 Salle salle, Session session) {
        this.id = id;
        this.numeroPlace = numeroPlace;
        this.estReservee = estReservee;
        this.salle = salle;
        this.session = session;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(Integer numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public Boolean getEstReservee() {
        return estReservee;
    }

    public void setEstReservee(Boolean estReservee) {
        this.estReservee = estReservee;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
