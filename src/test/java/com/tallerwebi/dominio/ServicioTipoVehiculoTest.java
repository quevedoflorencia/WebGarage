package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.TipoVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioTipoVehiculoTest {

    private RepositorioTipoVehiculo repositorioTipoVehiculo;
    private ServicioTipoVehiculo servicioTipoVehiculo;

    @BeforeEach
    public void init(){
        this.repositorioTipoVehiculo = mock(RepositorioTipoVehiculo.class);
        this.servicioTipoVehiculo = new ServicioTipoVehiculoImpl(repositorioTipoVehiculo);
    }

    @Test
    public void cuandoSeQuiereListarTodosLosTiposVehiculosPeroNoHayGuardadosDebeDevolverListaVacia() {
        when(repositorioTipoVehiculo.listar()).thenReturn(Collections.emptyList());

        List<TipoVehiculo> tipoVehiculos = servicioTipoVehiculo.traerTodos();

        assertThat(tipoVehiculos, is(notNullValue()));
        assertThat(tipoVehiculos, hasSize(0));
    }

    @Test
    public void cuandoSeQuiereListarTodosLosTiposVehiculosDebeDevolverLaListaCorrespondienteConDatos() {
        TipoVehiculo tipoVehiculo1 = new TipoVehiculo(1, "Auto", "icono.jpg");
        TipoVehiculo tipoVehiculo2 = new TipoVehiculo(2, "Moto", "icono.jpg");

        when(repositorioTipoVehiculo.listar()).thenReturn(Arrays.asList(tipoVehiculo1, tipoVehiculo2));

        List<TipoVehiculo> tipoVehiculos = servicioTipoVehiculo.traerTodos();

        assertThat(tipoVehiculos, is(notNullValue()));
        assertThat(tipoVehiculos, hasSize(2));
        assertThat(tipoVehiculos, containsInAnyOrder(tipoVehiculo1, tipoVehiculo2));
    }
}
