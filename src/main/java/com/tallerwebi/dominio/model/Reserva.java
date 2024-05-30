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
    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    private Pago pago;
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

    public Reserva(Long id, Usuario usuario, Garage garage, String dia, String horarioInicio, String horarioFin, Pago pago) {
        this.id = id;
        this.usuario = usuario;
        this.garage = garage;
        this.dia = dia;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.pago = pago;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
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

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
