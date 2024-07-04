package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.ReservaNotificacion;

public interface RepositorioNotificacion {
    void guardar(ReservaNotificacion reservaNotificacion);

    ReservaNotificacion buscarPorIdReserva(Long reservaID);
}
