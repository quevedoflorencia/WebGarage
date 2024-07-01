package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer puntaje;
    private String comentario;
    private Integer idGarage;


    public Calificacion(Integer id, Integer puntaje, String comentario, Integer idGarage) {
        this.id = id;
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.idGarage = idGarage;
    }

    public Calificacion() {

    }

    public Calificacion(Integer puntaje, String comentario, Integer idGarage) {
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.idGarage = idGarage;
    }

    public Calificacion(Integer idGarage, Integer puntaje, String comentario) {
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

    public Integer getIdGarage() {
        return idGarage;
    }

    public void setIdGarage(Integer idGarage) {
        this.idGarage = idGarage;
    }
}
