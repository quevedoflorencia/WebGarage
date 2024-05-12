package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoEncontrado;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.Reservacion;
import com.tallerwebi.presentacion.dto.ReservacionDTO;

import java.util.List;

public interface ServicioRepositorio {

    void agregarReserva(ReservacionDTO reservacionDTO) throws ExcepcionGarageNoEncontrado, ExcepcionUsuarioNoEncontrado;
    List traerHorasOcupadas(String day);
    List<Reservacion> obtenerReservasByUserId(Long id);

}



