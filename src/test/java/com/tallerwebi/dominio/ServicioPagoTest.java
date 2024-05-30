package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ServicioPagoTest {

    private RepositorioPago repositorioPago;
    private ServicioReserva servicioReserva;
    private ServicioPago servicioPago;

    @BeforeEach
    public void init(){
        this.repositorioPago = mock(RepositorioPago.class);
        this.servicioReserva = mock(ServicioReserva.class);
        this.servicioPago = new ServicioPagoImpl(repositorioPago, servicioReserva);
    }

}
