package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.EstadoReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ServicioEstadoReservaTest {

    private RepositorioEstadoReserva repositorioEstadoReserva;
    private ServicioEstadoReserva servicioEstadoReserva;

    @BeforeEach
    public void init(){
        this.repositorioEstadoReserva = mock(RepositorioEstadoReserva.class);
        this.servicioEstadoReserva = new ServicioEstadoReservaImpl(repositorioEstadoReserva);
    }

    @Test
    public void cuandoSeLlamaATraerTodosDebeDevolverUnaListaVaciaSiNoHayEstados() {
        when(repositorioEstadoReserva.listarEstadoReservas()).thenReturn(Arrays.asList());

        List<EstadoReserva> estados = servicioEstadoReserva.traerTodos();

        assertThat(estados, is(notNullValue()));
        assertThat(estados, hasSize(0));
    }

    @Test
    public void cuandoSeLlamaATraerTodosDebeDevolverUnaListaDeEstadosExistentes() {
        List<EstadoReserva> estadosSimulados = Arrays.asList(
                new EstadoReserva("Reservado"),
                new EstadoReserva("Cancelado")
        );

        when(repositorioEstadoReserva.listarEstadoReservas()).thenReturn(estadosSimulados);

        List<EstadoReserva> estados = servicioEstadoReserva.traerTodos();

        assertThat(estados, is(notNullValue()));
        assertThat(estados, hasSize(2));
        assertThat(estados, containsInAnyOrder(estadosSimulados.toArray(new EstadoReserva[0])));
    }

    @Test
    public void cuandoSeLlamaAObtenerEstadoSegunDescripcionDebeDevolverElEstadoCorrectoSegunLaDescripcionDada() {
        String descripcion = "Reservado";
        EstadoReserva estadoSimulado = new EstadoReserva(descripcion);

        when(repositorioEstadoReserva.getEstadoReservaByDescripcion(descripcion)).thenReturn(estadoSimulado);

        EstadoReserva estado = servicioEstadoReserva.obtenerEstadoSegunDescripcion(descripcion);

        assertThat(estado, is(notNullValue()));
        assertThat(estado.getDescripcion(), equalTo(descripcion));
    }

    @Test
    public void cuandoSeLlamaAObtenerEstadoSegunDescripcionDebeDevolverNullSiNoExiste() {
        String descripcion = "NoExiste";

        when(repositorioEstadoReserva.getEstadoReservaByDescripcion(descripcion)).thenReturn(null);

        EstadoReserva estado = servicioEstadoReserva.obtenerEstadoSegunDescripcion(descripcion);

        assertThat(estado, is(nullValue()));
    }
}
