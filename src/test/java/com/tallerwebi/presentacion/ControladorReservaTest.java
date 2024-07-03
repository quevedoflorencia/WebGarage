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
import static org.hamcrest.Matchers.equalTo;
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
    private ServicioEmail servicioEmail;
    private ServicioLogin servicioLoginMock;
    private ControladorLogin controladorLogin;

    @BeforeEach
    public void init() {
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioUsuario = mock(ServicioUsuario.class);
        servicioGarage = mock(ServicioGarage.class);
        servicioReserva = mock(ServicioReserva.class);
        servicioTipoVehiculo = mock(ServicioTipoVehiculo.class);
        servicioGarageTipoVehiculo = mock(ServicioGarageTipoVehiculo.class);
        servicioEmail = mock(ServicioEmailImpl.class);
        controladorReserva = new ControladorReserva(servicioUsuario, servicioGarage, servicioReserva, servicioTipoVehiculo, servicioGarageTipoVehiculo, servicioEmail);
        servicioLoginMock = mock(ServicioLogin.class);
        controladorLogin = new ControladorLogin(servicioLoginMock);
    }

    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadas() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);
        EstadoReserva estadoActiva = new EstadoReserva(EstadoReserva.ACTIVA, "Activa");

        List<Reserva> reservas = List.of(
                new Reserva(usuario, new Garage(), new GarageTipoVehiculo(), "2024-05-05", "04:00", "06:00", 100.0, estadoActiva),
                new Reserva(usuario, new Garage(), new GarageTipoVehiculo(), "2024-05-05", "20:00", "23:00", 200.0, estadoActiva)
        );

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioReserva.obtenerReservasByUserId(usuario.getId())).thenReturn(reservas);
        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);

        ModelAndView modelAndView = controladorReserva.listarReservas(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
        verify(servicioReserva).validarVencimientoReservas(reservas);
    }

    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadasAunqueLaListaEsteVacia() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);
        List<Reserva> reservas = Collections.emptyList();
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioReserva.obtenerReservasByUserId(usuario.getId())).thenReturn(reservas);
        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);

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
    public void listarReservasDebeDividirCorrectamenteEntreActivasYVencidas() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);
        EstadoReserva estadoVencido = new EstadoReserva(EstadoReserva.VENCIDA, "Vencida");
        EstadoReserva estadoActivo = new EstadoReserva(EstadoReserva.ACTIVA, "Activa");

        List<Reserva> reservas = List.of(
                new Reserva(usuario, new Garage(), new GarageTipoVehiculo(), "2024-05-05", "04:00", "06:00", 100.0, estadoVencido),
                new Reserva(usuario, new Garage(), new GarageTipoVehiculo(), "2024-05-05", "20:00", "23:00", 200.0, estadoActivo)
        );

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioReserva.obtenerReservasByUserId(usuario.getId())).thenReturn(reservas);
        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);

        ModelAndView modelAndView = controladorReserva.listarReservas(requestMock);

        List<Reserva> reservasActivas = (List<Reserva>) modelAndView.getModel().get("reservasActivas");
        List<Reserva> reservasVencidas = (List<Reserva>) modelAndView.getModel().get("reservasVencidas");

        assertThat(reservasActivas.size(), equalTo(1));
        assertThat(reservasVencidas.size(), equalTo(1));
    }

    @Test
    public void iniciarLaPreReservacionCorrectamenteConUsuarioLogueadoYGarageExistente() {
        Integer garageId = 1;
        Long userId = 1L;

        Garage garage = new Garage(garageId, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg");
        TipoVehiculo tipoVehiculoAuto = new TipoVehiculo(1, "Auto", "icono.png");
        TipoVehiculo tipoVehiculoMoto = new TipoVehiculo(2, "Moto", "icono2.png");

        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo(1, 100.0, garage, tipoVehiculoAuto, 1);
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

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login?from=garage/"+garageId));
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
        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo(1, 100.0, garage, new TipoVehiculo(1, "Auto", "icono.png"), 1);
        double precio = 200.0;
        when(servicioGarage.buscarPorId(garage.getId())).thenReturn(garage);
        when(servicioGarageTipoVehiculo.obtenerPorId(garageTipoVehiculo.getId())).thenReturn(garageTipoVehiculo);
        when(servicioReserva.calcularPrecio(reservaDTO.getHorarioInicio(), reservaDTO.getHorarioFin(), garageTipoVehiculo)).thenReturn(precio);

        ModelAndView modelAndView = controladorReserva.confirmar(reservaDTO, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("confirm-reservation"));
    }

    @Test
    public void guardarReservaCorrectamente() throws ExcepcionUsuarioNoEncontrado, ExcepcionGarageNoExiste {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(1);
        reservaDTO.setUserId(1L);
        reservaDTO.setPrecio(100.0);
        reservaDTO.setHorarioInicio("10:00");
        reservaDTO.setHorarioFin("12:00");
        reservaDTO.setGarageTipoVehiculoId(1);

        Garage garage = new Garage(1, "Garage 1", 50, LocalTime.parse("08:00:00"), LocalTime.parse("20:00:00"), "0.0", "0.0", "rutaFoto.jpg");
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);
        Reserva reserva = new Reserva(usuario, garage, new GarageTipoVehiculo(), "2024-05-05", reservaDTO.getHorarioInicio(), reservaDTO.getHorarioFin(), reservaDTO.getPrecio(), new EstadoReserva("Confirmada"));

        when(servicioReserva.agregarReserva(any(ReservaDTO.class))).thenReturn(reserva);

        ModelAndView modelAndView = controladorReserva.guardar(reservaDTO);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/pago/formulario-pago/" + reserva.getId()));
    }

    @Test
    public void fallaElGuardadoDeUnaReservaPorqueElGarageNoExiste() throws ExcepcionUsuarioNoEncontrado, ExcepcionGarageNoExiste {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(1);
        reservaDTO.setUserId(1L);
        reservaDTO.setPrecio(100.0);
        reservaDTO.setHorarioInicio("10:00");
        reservaDTO.setHorarioFin("12:00");
        reservaDTO.setGarageTipoVehiculoId(1);

        when(servicioReserva.agregarReserva(any(ReservaDTO.class))).thenThrow(new ExcepcionGarageNoExiste());

        ModelAndView modelAndView = controladorReserva.guardar(reservaDTO);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("confirm-reservation"));
    }

    @Test
    public void fallaElGuardadoDeUnaReservaPorqueElUsuarioNoExiste() throws ExcepcionUsuarioNoEncontrado, ExcepcionGarageNoExiste {
        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setGarageId(1);
        reservaDTO.setUserId(1L);
        reservaDTO.setPrecio(100.0);
        reservaDTO.setHorarioInicio("10:00");
        reservaDTO.setHorarioFin("12:00");
        reservaDTO.setGarageTipoVehiculoId(1);

        when(servicioReserva.agregarReserva(any(ReservaDTO.class))).thenThrow(new ExcepcionUsuarioNoEncontrado());

        ModelAndView modelAndView = controladorReserva.guardar(reservaDTO);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:../login"));
    }

    @Test
    public void cancelarReservaCorrectamente() {
        Long reservaId = 1L;
        doNothing().when(servicioReserva).cancelar(reservaId);

        ModelAndView modelAndView = controladorReserva.cancelar(reservaId);

        verify(servicioEmail).enviarMailReservaCancelada(any());

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/reservas/listar"));
    }
}
