package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ServicioGarageTipoVehiculoTest {

    private RepositorioGarageTipoVehiculo repositorioGarageTipoVehiculo;
    private ServicioGarageTipoVehiculo servicioGarageTipoVehiculo;

    @BeforeEach
    public void init(){
        this.repositorioGarageTipoVehiculo = mock(RepositorioGarageTipoVehiculo.class);
        this.servicioGarageTipoVehiculo = new ServicioGarageTipoVehiculoImpl(repositorioGarageTipoVehiculo);
    }

}
