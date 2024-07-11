package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface RepositorioGarage {

    List<Garage> findAll();
    Garage findById(Integer id);

    List<Garage> getGarageSegunCapacidad(Integer capacidadBuscada);
    List<Garage> obtenerPaginacion(Integer page, Integer size, Boolean orderByCalificacion, String busqueda);
    List<Garage> getGaragesPorTipoVehiculo(Integer tipoVehiculoId);
    Integer getCount(String busqueda);

    void guardarPromedio(Garage garage);
}
