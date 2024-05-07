package com.tallerwebi.dominio;

import java.util.List;

public interface ReservationRepository {

    List reservationByClient(String client);

    List reservationByDate(String date);

    List allReservations();

    List reservationByIdUser (Long id);

}

