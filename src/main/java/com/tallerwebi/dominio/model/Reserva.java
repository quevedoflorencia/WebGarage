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
    @OneToOne
    private GarageTipoVehiculo garageTipoVehiculo;
    private String dia;
    private String horarioInicio;
    private String horarioFin;
    private Double precio;
    @OneToOne
    private EstadoReserva estado;


    public Reserva() {}

    public Reserva(Usuario usuario, Garage garage, GarageTipoVehiculo garageTipoVehiculo, String dia, String horarioInicio, String horarioFin, Double precio, EstadoReserva estado) {
        this.usuario = usuario;
        this.garage = garage;
        this.garageTipoVehiculo = garageTipoVehiculo;
        this.dia = dia;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.precio = precio;
        this.estado = estado;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Usuario getUser() { return usuario; }

    public void setUser(Usuario usuario) { this.usuario = usuario; }

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

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

}
