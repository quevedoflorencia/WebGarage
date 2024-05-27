package com.tallerwebi.dominio;
import com.tallerwebi.dominio.model.EstadoReserva;
import com.tallerwebi.dominio.model.TipoVehiculo;

import java.util.List;

public interface RepositorioEstadoReserva {
    List<EstadoReserva> listarEstadoReservas();
    EstadoReserva getEstadoReservaByDescripcion(String descripcion);
}



