package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Calificacion;
import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface RepositorioCalificacion {
    void guardarCalificacion (Calificacion calificacion);


    List <Calificacion> getCalificacionesSegunGarage (Integer idGarage);
}
