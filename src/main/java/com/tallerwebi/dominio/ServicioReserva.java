package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoExiste;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.presentacion.dto.ReservaDTO;

import java.util.Collection;
import java.util.List;

public interface ServicioReserva {

    Reserva agregarReserva(ReservaDTO reservaDTO) throws ExcepcionGarageNoExiste, ExcepcionUsuarioNoEncontrado;

    List traerHorasOcupadas(String day);

    List<Reserva> obtenerReservasByUserId(Long id);

    List<Reserva> traerPorEstado(int estadoId);

    Reserva buscarPorId(Long reservaId);

    Double calcularPrecio(String horarioInicio, String horarioFin, GarageTipoVehiculo garageTipoVehiculo);

    void activar(Long reservaId);

    void cancelar(Long reservaId);

    void validarVencimientoReservas(List<Reserva> reservas);

    void validarActivarReservas(List<Reserva> reservas);

    boolean estaVencida(Reserva reserva);

    void pagar(Reserva reserva);

    Collection<String> traerHorasCierre(Integer garageId);

    Collection<String> traerHorasOcupadasPorDiaYTipoVehiculo(String selectedDate, Integer garageTipoVehiculoId);
}



