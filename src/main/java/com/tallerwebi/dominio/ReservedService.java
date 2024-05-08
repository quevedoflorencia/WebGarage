package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reservation;

import java.util.List;

public interface ReservedService {

    List getReservedHours(String day);
    Reservation getReservationByUserId (Long id);
    List obtenerReservasByUserId (Long id);
}
