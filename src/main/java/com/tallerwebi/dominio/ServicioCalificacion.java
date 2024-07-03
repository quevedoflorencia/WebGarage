package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface ServicioCalificacion {

    void guardarCalificacion (Integer puntaje, String comentario, Garage garage);
/*
    List getCalificacionesSegunGarage (Integer idGarage);


    Double calcularPromedio(Integer idGarage);*/
}
