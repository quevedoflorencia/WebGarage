package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioGarageTipoVehiculoTest {

    private RepositorioGarageTipoVehiculo repositorioGarageTipoVehiculo;
    private ServicioGarageTipoVehiculo servicioGarageTipoVehiculo;

    @BeforeEach
    public void init(){
        this.repositorioGarageTipoVehiculo = mock(RepositorioGarageTipoVehiculo.class);
        this.servicioGarageTipoVehiculo = new ServicioGarageTipoVehiculoImpl(repositorioGarageTipoVehiculo);
    }

    @Test
    public void cuandoSeEjecutaListarPeroNoHayGarageTipoVehiculosGuardadosDebeDevolverUnaListaVacia(){
        when(repositorioGarageTipoVehiculo.listar()).thenReturn(Collections.emptyList());

        List<GarageTipoVehiculo> garageTipoVehiculosVacio = servicioGarageTipoVehiculo.listar();

        assertThat(garageTipoVehiculosVacio, is(notNullValue()));
        assertThat(garageTipoVehiculosVacio, hasSize(0));
    }

    @Test
    public void cuandoSeEjecutaListarLosGaragesTipoVehiculos(){
        List<GarageTipoVehiculo> datosSimulados = Arrays.asList(
                new GarageTipoVehiculo(1, 1000.0, null, null),
                new GarageTipoVehiculo(2, 2000.0, null, null)
        );

        when(repositorioGarageTipoVehiculo.listar()).thenReturn(datosSimulados);

        List<GarageTipoVehiculo> garageTipoVehiculos = servicioGarageTipoVehiculo.listar();

        assertThat(garageTipoVehiculos, is(notNullValue()));
        assertThat(garageTipoVehiculos, hasSize(2));
        assertThat(garageTipoVehiculos, containsInAnyOrder(garageTipoVehiculos.toArray(new GarageTipoVehiculo[0])));
    }

    @Test
    public void cuandoSeEjecutaObtenerPorIdPeroNoHayGarageTipoVehiculoConElIdProvisto(){
        Integer idInvalido = 999;

        when(repositorioGarageTipoVehiculo.obtenerPorId(idInvalido)).thenReturn(null);

        GarageTipoVehiculo garageTipoVehiculo = servicioGarageTipoVehiculo.obtenerPorId(idInvalido);

        assertThat(garageTipoVehiculo, nullValue());
    }

    @Test
    public void cuandoSeEjecutaObtenerPorIdYSeObtieneElGarageTipoVehiculoCorrespondienteAlId(){
        Integer idValido = 1;
        GarageTipoVehiculo vehiculoSimulado = new GarageTipoVehiculo(1, 2000.0, null, null);
        when(repositorioGarageTipoVehiculo.obtenerPorId(idValido)).thenReturn(vehiculoSimulado);

        GarageTipoVehiculo garageTipoVehiculo = servicioGarageTipoVehiculo.obtenerPorId(idValido);

        assertThat(garageTipoVehiculo, is(notNullValue()));
        assertThat(garageTipoVehiculo.getId(), equalTo(idValido));
    }

}
