package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Calificacion;
import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface RepositorioCalificacion {

    void guardarCalificacion(Calificacion calificacion);

    List<Calificacion> obtenerPorGarageYOrden(Integer idGarage, String orden);

    List<Calificacion> buscarCalificacionPorId(Integer idGarage);
}
