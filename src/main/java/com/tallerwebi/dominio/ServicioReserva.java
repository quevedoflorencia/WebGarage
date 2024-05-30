package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoEncontrado;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.presentacion.dto.ReservaDTO;

import java.util.List;

public interface ServicioReserva {

    Reserva agregarReserva(ReservaDTO reservaDTO) throws ExcepcionGarageNoEncontrado, ExcepcionUsuarioNoEncontrado;
    List traerHorasOcupadas(String day);
    List<Reserva> obtenerReservasByUserId(Long id);
    Reserva buscarPorId (Long reservaId);
    Double calcularPrecio(String horarioInicio, String horarioFin, GarageTipoVehiculo garageTipoVehiculo);

    void cancelarReserva(Long reservaId);
}



