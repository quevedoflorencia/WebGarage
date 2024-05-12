package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reservacion;

import java.util.List;

public interface RepositorioReservacion {

    List reservasPorCliente(String client);

    List reservasPorFecha(String date);

    List todasLasReservas();


    List<Reservacion> obtenerReservasByUserId(Long id);

    void agregarNuevaReserva(Reservacion reservacion);

}

