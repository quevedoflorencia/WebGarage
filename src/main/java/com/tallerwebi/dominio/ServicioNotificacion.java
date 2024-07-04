package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.ReservaNotificacion;

public interface ServicioNotificacion {
    void guardar(ReservaNotificacion reservaNotificacion);

    ReservaNotificacion traerNotificacionPorReservaId(Long reservaId);
}
