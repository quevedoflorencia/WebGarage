package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Calificacion;
import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface ServicioCalificacion {

    void guardarCalificacion (Integer puntaje, String comentario, Garage garage);

    List<Calificacion> obtenerPorGarage(Integer idGarage, String orden);
}
