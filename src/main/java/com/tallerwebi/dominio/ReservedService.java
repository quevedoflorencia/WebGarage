package com.tallerwebi.dominio;

import java.util.List;

public interface ReservedService {

    List getReservedHours(String day);
    List getReservationByUserId (Long id);

}
