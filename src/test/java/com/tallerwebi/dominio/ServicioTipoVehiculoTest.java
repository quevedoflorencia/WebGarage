package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ServicioTipoVehiculoTest {

    private RepositorioTipoVehiculo repositorioTipoVehiculo;
    private ServicioTipoVehiculo servicioTipoVehiculo;

    @BeforeEach
    public void init(){
        this.repositorioTipoVehiculo = mock(RepositorioTipoVehiculo.class);
        this.servicioTipoVehiculo = new ServicioTipoVehiculoImpl(repositorioTipoVehiculo);
    }
}
