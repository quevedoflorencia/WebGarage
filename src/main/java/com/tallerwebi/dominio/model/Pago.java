package com.tallerwebi.dominio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_reserva;

    public Pago() {}

    public Pago(Long id, Long id_reserva) {
        this.id = id;
        this.id_reserva = id_reserva;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(Long id_reserva) {
        this.id_reserva = id_reserva;
    }
}
