package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.ReservaNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioNotificacion")
@Transactional
public class ServicioNotificacionImpl implements ServicioNotificacion {

    private RepositorioNotificacion repositorioNotificacion;

    @Autowired
    public ServicioNotificacionImpl(RepositorioNotificacion repositorioNotificacion) {
        this.repositorioNotificacion = repositorioNotificacion;
    }

    @Override
    public void guardar(ReservaNotificacion reservaNotificacion) {
        repositorioNotificacion.guardar(reservaNotificacion);
    }

    @Override
    public ReservaNotificacion traerNotificacionPorReservaId(Long reservaId) {
        return repositorioNotificacion.buscarPorIdReserva(reservaId);
    }
}
