package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Usuario usuario;
    @OneToOne
    private Garage garage;
    private String day;
    private String startTime;
    private String finishTime;

    public Reservacion() {}

    public Reservacion(Long id, Usuario usuario, Garage garage, String day, String startTime, String finishTime) {
        this.id = id;
        this.usuario = usuario;
        this.garage = garage;
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Usuario getUser() { return usuario; }

    public void setUser(Usuario usuario) { this.usuario = usuario; }

    public Garage getGarage() { return garage; }

    public void setGarage(Garage garage) { this.garage = garage; }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

}
