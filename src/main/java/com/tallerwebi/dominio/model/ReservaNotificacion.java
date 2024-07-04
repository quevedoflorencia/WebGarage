package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
public class ReservaNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

    public ReservaNotificacion(Reserva reserva) {
        this.reserva = reserva;
    }

    public ReservaNotificacion() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
