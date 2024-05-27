package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.EstadoReserva;

import java.util.List;

public interface ServicioEstadoReserva {
    List<EstadoReserva> traerTodos();
    EstadoReserva obtenerEstadoSegunDescripcion(String descripcion);


}
