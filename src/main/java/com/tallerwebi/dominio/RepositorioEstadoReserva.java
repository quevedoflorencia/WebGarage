package com.tallerwebi.dominio;
import com.tallerwebi.dominio.model.EstadoReserva;

import java.util.List;

public interface RepositorioEstadoReserva {
    List<EstadoReserva> listar();
    EstadoReserva obtenerPorId(Integer id);
    EstadoReserva obtenerPorDescripcion(String descripcion);
}



