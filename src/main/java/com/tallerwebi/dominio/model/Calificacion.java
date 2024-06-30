package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer puntaje;
    private String comentarioCalificacion;

    @ManyToOne
    @JoinColumn (name = "id")
    private Garage garage;



    public Calificacion(Integer id, Integer puntaje, String comentarioCalificacion) {
        this.id = id;
        this.puntaje = puntaje;
        this.comentarioCalificacion = comentarioCalificacion;
    }

    public Calificacion(Garage garage, Integer puntaje, String comentarioCalificacion) {
        this.garage = garage;
        this.puntaje = puntaje;
        this.comentarioCalificacion = comentarioCalificacion;
    }

    public Calificacion(Integer id, Integer puntaje) {
        this.id = id;
        this.puntaje = puntaje;
    }

    public Calificacion(Integer puntaje, String comentarioCalificacion) {
        this.puntaje = puntaje;
        this.comentarioCalificacion = comentarioCalificacion;
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


}
