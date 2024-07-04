package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reserva;

import java.util.List;

public interface RepositorioReserva {

    Reserva guardar(Reserva reserva);

    void actualizar(Reserva reserva);

    Reserva obtenerPorId(Long reservaId);

    List reservasPorFecha(String date);

    List<Reserva> obtenerPorUserId(Long id);

    List<Reserva> obtenerImpagas(int estadoId);

    List reservasPorFechaYTipoDeAuto(String selectedDate, int garageTipoVehiculoId);
}

