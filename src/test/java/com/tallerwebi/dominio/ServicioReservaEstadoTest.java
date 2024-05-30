package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ServicioReservaEstadoTest {

    private RepositorioEstadoReserva repositorioEstadoReserva;
    private ServicioEstadoReserva servicioEstadoReserva;

    @BeforeEach
    public void init(){
        this.repositorioEstadoReserva = mock(RepositorioEstadoReserva.class);
        this.servicioEstadoReserva = new ServicioReservaEstadoImpl(repositorioEstadoReserva);
    }

}
