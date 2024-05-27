package com.tallerwebi.dominio;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import java.util.List;

public interface ServicioGarageTipoVehiculo {
    List<GarageTipoVehiculo> traerTodos();
    GarageTipoVehiculo traerPorId(Integer id);


}
