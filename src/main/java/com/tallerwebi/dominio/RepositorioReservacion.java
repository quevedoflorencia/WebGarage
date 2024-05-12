package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reservacion;

import java.util.List;

public interface RepositorioReservacion {

    List reservationByClient(String client);

    List reservationByDate(String date);

    List allReservations();

    Reservacion reservationByIdUser (Long id);

    List<Reservacion> obtenerReservasByUserId(Long id);

    void addNewReservation(Reservacion reservacion);

}

