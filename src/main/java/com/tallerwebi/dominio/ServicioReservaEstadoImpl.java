package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.EstadoReserva;
import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioEstadoReserva")
@Transactional
public class ServicioReservaEstadoImpl implements ServicioEstadoReserva {

    private RepositorioEstadoReserva repositorioEstadoReserva;

    @Autowired
    public ServicioReservaEstadoImpl(RepositorioEstadoReserva repositorioEstadoReserva) { this.repositorioEstadoReserva = repositorioEstadoReserva; }

    @Override
    public List<EstadoReserva> traerTodos() {
        return repositorioEstadoReserva.listarEstadoReservas();
    }

    @Override
    public EstadoReserva obtenerEstadoSegunDescripcion(String descripcion) {
        return repositorioEstadoReserva.getEstadoReservaByDescripcion(descripcion);
    }

}
