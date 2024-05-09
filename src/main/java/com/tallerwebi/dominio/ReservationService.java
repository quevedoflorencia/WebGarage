package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.GarageNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.presentacion.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {

    void addReservation(ReservationDTO reservationDTO) throws GarageNotFoundException, UserNotFoundException;
    List getReservedHours(String day);
    Reservation getReservationByUserId(Long id);
    List<Reservation> obtenerReservasByUserId(Long id);

}



