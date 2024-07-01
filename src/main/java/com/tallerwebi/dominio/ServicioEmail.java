package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reserva;

public interface ServicioEmail {
    void enviarMailReservaCancelada(Reserva reserva);
    void enviarMailReservaExitosa(Reserva reserva);
}
