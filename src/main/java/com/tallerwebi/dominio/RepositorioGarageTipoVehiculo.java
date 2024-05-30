package com.tallerwebi.dominio;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import com.tallerwebi.dominio.model.TipoVehiculo;

import java.util.List;

public interface RepositorioGarageTipoVehiculo {
    List<GarageTipoVehiculo> listarGarageTiposVehiculos();
    GarageTipoVehiculo obtenerById(Integer id);
}



