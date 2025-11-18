package com.example.TickNet.Dto;

public class MyPlace { private Integer placeId;
    private Integer numeroPlace;
    private Boolean reservee;

    public MyPlace() {
        super();
    }

    public MyPlace(Integer placeId, Integer numeroPlace, Boolean reservee) {
        this.placeId = placeId;
        this.numeroPlace = numeroPlace;
        this.reservee = reservee;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(Integer numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public Boolean getReservee() {
        return reservee;
    }

    public void setReservee(Boolean reservee) {
        this.reservee = reservee;
    }

    @Override
    public String toString() {
        return "MyPlace [placeId=" + placeId + ", numeroPlace=" + numeroPlace + ", reservee=" + reservee + "]";
    }
}
