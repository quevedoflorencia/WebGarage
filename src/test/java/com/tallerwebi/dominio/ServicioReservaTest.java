package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoExiste;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.presentacion.dto.ReservaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

        this.servicioReserva = new ServicioReservaImpl(
                repositorioReserva,
                servicioGarage,
                servicioUsuario,
                servicioGarageTipoVehiculo,
                servicioEstadoReserva
        );
    }

    @Test
    public void cuandoSeQuiereCrearUnaReservaYElUsuarioDadoNoExisteDebeLanzarExcepcionDeUsuarioNoEncontrado() {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.userId = 1L;

        when(servicioUsuario.get(reservaDTO.userId)).thenReturn(null);

        assertThrows(ExcepcionUsuarioNoEncontrado.class, () -> servicioReserva.agregarReserva(reservaDTO));
    }

    @Test
    public void cuandoSeQuiereCrearUnaReservaYElGarageNoExisteDebeLanzarUnaExcepcionDeGarageNoExiste() {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.userId = 1L;
        reservaDTO.garageId = 1;

        Usuario usuario = new Usuario();
        when(servicioUsuario.get(reservaDTO.userId)).thenReturn(usuario);
        when(servicioGarage.buscarPorId(reservaDTO.garageId)).thenReturn(null);

        assertThrows(ExcepcionGarageNoExiste.class, () -> servicioReserva.agregarReserva(reservaDTO));
    }

    @Test
    public void cuandoSeQuiereCrearUnaReservaYTodosLosDatosSonCorrectosDebeGuardarCorrectamente() throws ExcepcionGarageNoExiste, ExcepcionUsuarioNoEncontrado {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.userId = 1L;
        reservaDTO.garageId = 1;
        reservaDTO.garageTipoVehiculoId = 1;
        reservaDTO.dia = "2023-05-30";
        reservaDTO.horarioInicio = "10:00";
        reservaDTO.horarioFin = "12:00";
        reservaDTO.precio = 200.0;

        Usuario usuario = new Usuario();
        Garage garage = new Garage();
        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo();
        EstadoReserva estadoReserva = new EstadoReserva("Pendiente");

        when(servicioUsuario.get(reservaDTO.userId)).thenReturn(usuario);
        when(servicioGarage.buscarPorId(reservaDTO.garageId)).thenReturn(garage);
        when(servicioGarageTipoVehiculo.obtenerPorId(reservaDTO.garageTipoVehiculoId)).thenReturn(garageTipoVehiculo);
        when(servicioEstadoReserva.obtenerEstadoSegunDescripcion("Pendiente")).thenReturn(estadoReserva);

        Reserva reserva = servicioReserva.agregarReserva(reservaDTO);

        verify(repositorioReserva, times(1)).guardar(reserva);
        assertThat(reserva, is(notNullValue()));
        assertThat(reserva.getUsuario(), is(usuario));
        assertThat(reserva.getGarage(), is(garage));
        assertThat(reserva.getEstado(), is(estadoReserva));
    }

    @Test
    public void cuandoSeBuscanLasHorasOcupadasDeUnGarageDebeDevolverLasHorasCorrectasSegunDisponibilidad() {
        String dia = "2023-05-30";
        Reserva reserva1 = new Reserva();
        reserva1.setHorarioInicio("10:00");
        reserva1.setHorarioFin("12:00");
        Reserva reserva2 = new Reserva();
        reserva2.setHorarioInicio("14:00");
        reserva2.setHorarioFin("16:00");

        when(repositorioReserva.reservasPorFecha(dia)).thenReturn(Arrays.asList(reserva1, reserva2));

        List<String> horasOcupadas = servicioReserva.traerHorasOcupadas(dia);

        assertThat(horasOcupadas, containsInAnyOrder("10", "11", "14", "15"));
    }

    @Test
    public void cuandoSeQuiereBuscarLasReservasDeUnUsuarioPorIdDebeDevolverLasReservasCorrectasDeEseUsuario() {
        Long userId = 1L;
        Reserva reserva1 = new Reserva();
        Reserva reserva2 = new Reserva();

        when(repositorioReserva.obtenerPorUserId(userId)).thenReturn(Arrays.asList(reserva1, reserva2));

        List<Reserva> reservas = servicioReserva.obtenerReservasByUserId(userId);

        assertThat(reservas, hasSize(2));
        assertThat(reservas, contains(reserva1, reserva2));
    }

    @Test
    public void cuandoSeBuscaUnaReservaPorIdDebeDevolverLaReservaCorrespondiente() {
        Long reservaId = 1L;
        Reserva reserva = new Reserva();

        when(repositorioReserva.obtenerPorId(reservaId)).thenReturn(reserva);

        Reserva result = servicioReserva.buscarPorId(reservaId);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(reserva));
    }

    @Test
    public void cuandoSeEjecutaCancelarReservaDebeActualizarEstadoACancelado() {
        String descripcionCancelado = "Cancelado";
        Long reservaId = 1L;
        Reserva reserva = new Reserva();
        EstadoReserva estadoCancelado = new EstadoReserva(descripcionCancelado);

        when(repositorioReserva.obtenerPorId(reservaId)).thenReturn(reserva);
        when(servicioEstadoReserva.obtenerEstadoSegunDescripcion(descripcionCancelado)).thenReturn(estadoCancelado);

        servicioReserva.cancelar(reservaId);

        assertThat(reserva.getEstado(), is(estadoCancelado));
        verify(repositorioReserva, times(1)).actualizar(reserva);
    }

    @Test
    public void cuandoSeBuscaCalcularUnPrecioDebeRetornarPrecioCorrectoSegunLaCantidadDeHorasDeInicioAFin() {
        String horarioInicio = "10:00";
        String horarioFin = "12:00";
        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo();
        garageTipoVehiculo.setPrecioHora(100.0);

        Double precio = servicioReserva.calcularPrecio(horarioInicio, horarioFin, garageTipoVehiculo);

        assertThat(precio, is(200.0));
    }
}
