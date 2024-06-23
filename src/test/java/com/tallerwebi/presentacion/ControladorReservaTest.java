/*
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoExiste;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.presentacion.dto.ReservaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorReservaTest {

    private ServicioUsuario servicioUsuario;
    private ServicioGarage servicioGarage;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioReserva servicioReserva;
    private ControladorReserva controladorReserva;
    private ServicioTipoVehiculo servicioTipoVehiculo;
    private ServicioGarageTipoVehiculo servicioGarageTipoVehiculo;

    @BeforeEach
    public void init() {
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioUsuario = mock(ServicioUsuario.class);
        servicioGarage = mock(ServicioGarage.class);
        servicioReserva = mock(ServicioReserva.class);
        servicioTipoVehiculo = mock(ServicioTipoVehiculo.class);
        servicioGarageTipoVehiculo = mock(ServicioGarageTipoVehiculo.class);
        controladorReserva = new ControladorReserva(servicioUsuario, servicioGarage, servicioReserva, servicioTipoVehiculo, servicioGarageTipoVehiculo);
    }

    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadas() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);
        List<Reserva> reservas = List.of(
                new Reserva(usuario, new Garage(), null, "2024-05-05", "04:00", "06:00", 100.0, null),
                new Reserva(usuario, new Garage(), null, "2024-05-05", "20:00", "23:00", 200.0, null)
        );
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioReserva.obtenerReservasByUserId(usuario.getId())).thenReturn(reservas);
        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);
        when(servicioGarage.traerTodos()).thenReturn(List.of(new Garage(1, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg")));

        ModelAndView modelAndView = controladorReserva.listarReservas(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
    }

    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadasAunqueLaListaEsteVacia() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);
        List<Reserva> reservas = Collections.emptyList();
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioReserva.obtenerReservasByUserId(usuario.getId())).thenReturn(reservas);
        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);
        when(servicioGarage.traerTodos()).thenReturn(List.of(new Garage(1, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg")));

        ModelAndView modelAndView = controladorReserva.listarReservas(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
    }

    @Test
    public void listaDeReservaDebeLlevarteALoginSiNoHayUsuarioLogueado() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(null);

        ModelAndView modelAndView = controladorReserva.listarReservas(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void iniciarLaPreReservacionCorrectamenteConUsuarioLogueadoYGarageExistente() {
        Integer garageId = 1;
        Long userId = 1L;

        Garage garage = new Garage(garageId, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg");
        TipoVehiculo tipoVehiculoAuto = new TipoVehiculo(1, "Auto", "icono.png");
        TipoVehiculo tipoVehiculoMoto = new TipoVehiculo(1, "Moto", "icono2.png");

        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo(1, 100.0, garage, tipoVehiculoAuto);
        garage.setGarageTipoVehiculos(Collections.singletonList(garageTipoVehiculo));

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(userId);
        when(servicioGarage.buscarPorId(garageId)).thenReturn(garage);
        when(servicioGarageTipoVehiculo.listar()).thenReturn(Collections.singletonList(garageTipoVehiculo));
        when(servicioTipoVehiculo.traerTodos()).thenReturn(Arrays.asList(tipoVehiculoAuto, tipoVehiculoMoto));

        ModelAndView modelAndView = controladorReserva.preReserva(requestMock, garageId);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("pre-reservation"));
    }

    @Test
    public void intentaIniciarLaPreReservacionConUsuarioNoLogueadoYDeberiaLlevarteAlLogin() {
        Integer garageId = 1;
        Garage garage = new Garage(garageId, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg");
        when(servicioGarage.buscarPorId(garageId)).thenReturn(garage);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(null);

        ModelAndView modelAndView = controladorReserva.preReserva(requestMock, garageId);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void intentaIniciarLaPreReservacionConUnGarageNoExistenteYDeberiaLlevarteAlHome() {
        Integer garageId = 1;
        Long userId = 1L;
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(userId);
        when(servicioGarage.buscarPorId(garageId)).thenReturn(null);

        ModelAndView modelAndView = controladorReserva.preReserva(requestMock, garageId);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:../home"));
    }

    @Test
    public void empiezaElProcesoDeConfirmacionDeUnaReservaValida() {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(1);
        reservaDTO.setUserId(1L);
        reservaDTO.setHorarioInicio("10:00");
        reservaDTO.setHorarioFin("12:00");
        reservaDTO.setGarageTipoVehiculoId(1);

        Garage garage = new Garage(1, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg");
        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo(1, 100.0, garage, new TipoVehiculo(1, "Auto", "icono.png"));

        when(servicioGarage.buscarPorId(reservaDTO.getGarageId())).thenReturn(garage);
        when(servicioGarageTipoVehiculo.obtenerPorId(reservaDTO.getGarageTipoVehiculoId())).thenReturn(garageTipoVehiculo);
        when(servicioReserva.calcularPrecio(reservaDTO.getHorarioInicio(), reservaDTO.getHorarioFin(), garageTipoVehiculo)).thenReturn(200.0);

        ModelAndView modelAndView = controladorReserva.confirmar(reservaDTO, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("confirm-reservation"));
    }

    @Test
    public void guardaUnaReservaCorrectamente() throws ExcepcionUsuarioNoEncontrado, ExcepcionGarageNoExiste {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(1);
        reservaDTO.setUserId(1L);
        reservaDTO.setHorarioInicio("10:00");
        reservaDTO.setHorarioFin("12:00");
        reservaDTO.setGarageTipoVehiculoId(1);
        reservaDTO.setPrecio(200.0);

        Reserva reserva = new Reserva(1L, new Usuario(), new Garage(), "2024-05-05", "10:00", "12:00", new Pago());
        when(servicioReserva.agregarReserva(reservaDTO)).thenReturn(reserva);

        ModelAndView modelAndView = controladorReserva.guardar(reservaDTO);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/pago/formulario-pago/" + reserva.getId()));
    }

    @Test
    public void intentaGuardarUnaReservaPeroElGarageNoExiste() throws ExcepcionUsuarioNoEncontrado, ExcepcionGarageNoExiste {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(1);
        reservaDTO.setUserId(1L);
        reservaDTO.setHorarioInicio("10:00");
        reservaDTO.setHorarioFin("12:00");
        reservaDTO.setGarageTipoVehiculoId(1);
        reservaDTO.setPrecio(200.0);

        when(servicioReserva.agregarReserva(reservaDTO)).thenThrow(new ExcepcionGarageNoExiste());

        List<Garage> garages = List.of(new Garage(1, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg"));
        when(servicioGarage.traerTodos()).thenReturn(garages);

        ModelAndView modelAndView = controladorReserva.guardar(reservaDTO);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("confirm-reservation"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al intentar guardar la reserva. Por favor, intente nuevamente"));
    }

    @Test
    public void intentaGuardarUnaReservaPeroElUsuarioNoExisteEntoncesVuelveAlLogin() throws ExcepcionUsuarioNoEncontrado, ExcepcionGarageNoExiste {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(1);
        reservaDTO.setUserId(1L);
        reservaDTO.setHorarioInicio("10:00");
        reservaDTO.setHorarioFin("12:00");
        reservaDTO.setGarageTipoVehiculoId(1);
        reservaDTO.setPrecio(200.0);

        when(servicioReserva.agregarReserva(reservaDTO)).thenThrow(new ExcepcionUsuarioNoEncontrado());

        List<Garage> garages = List.of(new Garage(1, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg"));
        when(servicioGarage.traerTodos()).thenReturn(garages);

        ModelAndView modelAndView = controladorReserva.guardar(reservaDTO);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:../login"));
    }

    @Test
    public void cancelarReservaDebeRedirigirALaListaDeReservas() {
        // Arrange
        Long reservaId = 1L;
        ModelAndView expectedModelAndView = new ModelAndView("redirect:/reservas/listar");

        // Act
        ModelAndView modelAndView = controladorReserva.cancelar(reservaId);

        // Assert
        assertThat(modelAndView.getViewName(), equalToIgnoringCase(expectedModelAndView.getViewName()));
        verify(servicioReserva, times(1)).cancelar(reservaId);
    }
}
*/