package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

public interface ServicioCalificacion {
    void guardarCalificacion (Integer puntaje, String comentarioCalificacion, Integer idGarage);

    void validarPuntaje(Integer puntaje);


}
