package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ServicioReservaTest {

    private RepositorioReserva repositorioReserva;
    private ServicioGarage servicioGarage;
    private ServicioUsuario servicioUsuario;
    private ServicioGarageTipoVehiculo servicioGarageTipoVehiculo;
    private ServicioEstadoReserva servicioEstadoReserva;
    private ServicioReserva servicioReserva;

    @BeforeEach
    public void init(){
        this.repositorioReserva = mock(RepositorioReserva.class);
        this.servicioGarage = mock(ServicioGarage.class);
        this.servicioUsuario = mock(ServicioUsuario.class);
        this.servicioGarageTipoVehiculo = mock(ServicioGarageTipoVehiculo.class);
        this.servicioEstadoReserva = mock(ServicioEstadoReserva.class);
        this.servicioReserva = new ServicioReservaImpl(repositorioReserva, servicioGarage, servicioUsuario, servicioGarageTipoVehiculo, servicioEstadoReserva);
    }

}
