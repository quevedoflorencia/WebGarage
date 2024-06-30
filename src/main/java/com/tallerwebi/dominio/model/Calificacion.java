package com.tallerwebi.dominio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Calificacion {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer puntaje;
    private String comentarioCalificacion;
    private Reserva reserva;

    public Calificacion(Integer id, Integer puntaje, String comentarioCalificacion) {
        this.id = id;
        this.puntaje = puntaje;
        this.comentarioCalificacion = comentarioCalificacion;
    }

    public Calificacion(Integer id, Integer puntaje, Reserva reserva) {
        this.id = id;
        this.puntaje = puntaje;
        this.reserva = reserva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentarioCalificacion() {
        return comentarioCalificacion;
    }

    public void setComentarioCalificacion(String comentarioCalificacion) {
        this.comentarioCalificacion = comentarioCalificacion;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
