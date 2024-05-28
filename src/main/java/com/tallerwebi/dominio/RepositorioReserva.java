package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reserva;

import java.util.List;

public interface RepositorioReserva {

    List reservasPorCliente(String client);

    List reservasPorFecha(String date);



    List<Reserva> obtenerReservasByUserId(Long id);

    void agregarNuevaReserva(Reserva reserva);

    Reserva obtenerReservasByReservaId(Long reservaId);


    void modificarReserva(Reserva reserva);
}

