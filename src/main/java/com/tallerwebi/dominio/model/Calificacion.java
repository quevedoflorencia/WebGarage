package com.tallerwebi.dominio.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "garage_id")
    private Garage garage;

    private Integer puntaje;

    private String comentario;

    private LocalDateTime fechaCreacion;

    public Calificacion(Integer puntaje, String comentario, Garage garage, LocalDateTime fechaCreacion) {
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.garage = garage;
        this.fechaCreacion = fechaCreacion;
    }

    public Calificacion() {}

    public Calificacion(Integer puntaje, String comentario) {
        this.puntaje = puntaje;
        this.comentario = comentario;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
