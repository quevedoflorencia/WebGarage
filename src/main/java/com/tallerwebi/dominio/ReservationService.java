package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.presentacion.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {

    void addReservation(ReservationDTO reservationDTO);
    List getReservedHours(String day);
    Reservation getReservationByUserId (Long id);
    List obtenerReservasByUserId (Long id);

}



