package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.GarageNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.model.Reservacion;
import com.tallerwebi.presentacion.dto.ReservacionDTO;

import java.util.List;

public interface ServicioRepositorio {

    void addReservation(ReservacionDTO reservacionDTO) throws GarageNotFoundException, UserNotFoundException;
    List traerHorasOcupadas(String day);
    Reservacion getReservationByUserId(Long id);
    List<Reservacion> obtenerReservasByUserId(Long id);

}



