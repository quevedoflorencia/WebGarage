package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.GarageTipoVehiculo;

import java.util.List;

public interface ServicioGarageTipoVehiculo {
    List<GarageTipoVehiculo> listar();
    GarageTipoVehiculo obtenerPorId(Integer id);


}
