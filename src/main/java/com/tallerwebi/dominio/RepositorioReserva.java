package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reserva;

import java.util.List;

public interface RepositorioReserva {

    List reservasPorCliente(String client);

    List reservasPorFecha(String date);

    List todasLasReservas();


    List<Reserva> obtenerReservasByUserId(Long id);

    void agregarNuevaReserva(Reserva reserva);

}

