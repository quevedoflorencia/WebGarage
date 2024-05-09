package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.presentacion.dto.ReservationDTO;

import java.util.List;

public interface ReservationRepository {

    List reservationByClient(String client);

    List reservationByDate(String date);

    List allReservations();

    void addNewReservation(ReservationDTO reservationDTO);

}

