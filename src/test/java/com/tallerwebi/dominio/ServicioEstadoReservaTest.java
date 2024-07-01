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
        when(repositorioEstadoReserva.listar()).thenReturn(Arrays.asList());

        List<EstadoReserva> estados = servicioEstadoReserva.traerTodos();

        assertThat(estados, is(notNullValue()));
        assertThat(estados, hasSize(0));
    }

    @Test
    public void cuandoSeLlamaATraerTodosDebeDevolverUnaListaDeEstadosExistentes() {
        List<EstadoReserva> estadosSimulados = Arrays.asList(
                new EstadoReserva(EstadoReserva.ACTIVA, "Activa"),
                new EstadoReserva(EstadoReserva.CANCELADA, "Cancelada")
        );

        when(repositorioEstadoReserva.listar()).thenReturn(estadosSimulados);

        List<EstadoReserva> estados = servicioEstadoReserva.traerTodos();

        assertThat(estados, is(notNullValue()));
        assertThat(estados, hasSize(2));
        assertThat(estados, containsInAnyOrder(estadosSimulados.toArray(new EstadoReserva[0])));
    }

    @Test
    public void cuandoSeLlamaAObtenerPorIdDebeDevolverElEstadoCorrectoSegunElIdDado() {
        EstadoReserva estadoActiva = new EstadoReserva(EstadoReserva.ACTIVA, "Activa");

        when(repositorioEstadoReserva.obtenerPorId(EstadoReserva.ACTIVA)).thenReturn(estadoActiva);

        EstadoReserva estado = servicioEstadoReserva.obtenerPorId(EstadoReserva.ACTIVA);

        assertThat(estado, is(notNullValue()));
        assertThat(estado.getId(), equalTo(EstadoReserva.ACTIVA));
    }

    @Test
    public void cuandoSeLlamaAObtenerPorIdDebeDevolverNullSiNoExiste() {
        Integer idFalso = 999;

        when(repositorioEstadoReserva.obtenerPorId(idFalso)).thenReturn(null);

        EstadoReserva estado = servicioEstadoReserva.obtenerPorId(idFalso);

        assertThat(estado, is(nullValue()));
    }

    @Test
    public void cuandoSeLlamaAObtenerPorDescripcionDebeDevolverElEstadoCorrectoSegunLaDescripcionDada() {
        String descripcion = "Activa";
        EstadoReserva estadoSimulado = new EstadoReserva(descripcion);

        when(repositorioEstadoReserva.obtenerPorDescripcion(descripcion)).thenReturn(estadoSimulado);

        EstadoReserva estado = servicioEstadoReserva.obtenerPorDescripcion(descripcion);

        assertThat(estado, is(notNullValue()));
        assertThat(estado.getDescripcion(), equalTo(descripcion));
    }

    @Test
    public void cuandoSeLlamaAObtenerPorDescripcionDebeDevolverNullSiNoExiste() {
        String descripcion = "NoExiste";

        when(repositorioEstadoReserva.obtenerPorDescripcion(descripcion)).thenReturn(null);

        EstadoReserva estado = servicioEstadoReserva.obtenerPorDescripcion(descripcion);

        assertThat(estado, is(nullValue()));
    }
}
