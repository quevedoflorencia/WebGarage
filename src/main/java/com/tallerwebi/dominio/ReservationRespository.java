package com.tallerwebi.dominio;

import java.util.List;

public interface ReservationRespository {
    List reservationByClient(String client);

    List reservationByDate(String date);

    List allReservations();


}

