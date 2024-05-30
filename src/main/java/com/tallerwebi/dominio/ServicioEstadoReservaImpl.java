package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.EstadoReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioEstadoReserva")
@Transactional
public class ServicioEstadoReservaImpl implements ServicioEstadoReserva {

    private RepositorioEstadoReserva repositorioEstadoReserva;

    @Autowired
    public ServicioEstadoReservaImpl(RepositorioEstadoReserva repositorioEstadoReserva) {
        this.repositorioEstadoReserva = repositorioEstadoReserva;
    }

    @Override
    public List<EstadoReserva> traerTodos() {
        return repositorioEstadoReserva.listarEstadoReservas();
    }

    @Override
    public EstadoReserva obtenerEstadoSegunDescripcion(String descripcion) {
        return repositorioEstadoReserva.getEstadoReservaByDescripcion(descripcion);
    }

}
