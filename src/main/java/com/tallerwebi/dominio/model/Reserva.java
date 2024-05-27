package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Usuario usuario;
    @OneToOne
    private Garage garage;
    private String dia;
    private String horarioInicio;
    private String horarioFin;

    public Reserva() {}

    public Reserva(Long id, Usuario usuario, Garage garage, String dia, String horarioInicio, String horarioFin) {
        this.id = id;
        this.usuario = usuario;
        this.garage = garage;
        this.dia = dia;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Garage getGarage() { return garage; }

    public void setGarage(Garage garage) { this.garage = garage; }

    public String getDia() {
        return dia;
    }

    public void setDia(String day) {
        this.dia = day;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String startTime) {
        this.horarioInicio = startTime;
    }

    public String getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(String finishTime) {
        this.horarioFin = finishTime;
    }

}
