package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

public interface ServicioCalificacion {
    void guardarCalificacion (Garage garage, Integer puntaje, String comentarioCalificacion);

    void validarPuntaje(Integer puntaje);


}
