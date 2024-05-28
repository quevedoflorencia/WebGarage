package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Reserva reserva;

    public Pago() {}

    public Pago(Long id, Reserva reserva) {
        this.id = id;
        this.reserva = reserva;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reserva getReserva() { return reserva; }

    public void setReserva(Reserva reserva) { this.reserva = reserva; }
}
