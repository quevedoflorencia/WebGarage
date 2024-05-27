package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.dominio.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservaControllerTest {

    private ServicioUsuario servicioUsuario;
    private ServicioGarage servicioGarage;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioReserva servicioReserva;
    private ControladorReserva controladorReserva;
    private ServicioTipoVehiculo servicioTipoVehiculo;
    private ServicioGarageTipoVehiculo servicioGarageTipoVehiculo;
    private ServicioEstadoReserva servicioEstadoReserva;

    @BeforeEach
    public void init(){
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioUsuario = mock(ServicioUsuario.class);
        servicioGarage = mock(ServicioGarage.class);
        servicioReserva = mock(ServicioReserva.class);
        servicioTipoVehiculo = mock(ServicioTipoVehiculo.class);
        servicioGarageTipoVehiculo = mock(ServicioGarageTipoVehiculo.class);
        servicioEstadoReserva = mock(ServicioEstadoReserva.class);
        controladorReserva = new ControladorReserva(servicioUsuario, servicioGarage, servicioReserva, servicioTipoVehiculo, servicioGarageTipoVehiculo, servicioEstadoReserva);
    }

/*
    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadas() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);

        List<Garage> garage = List.of(new Garage(null, "Suipacha", 5, LocalTime.parse("10:00:00"), LocalTime.parse("17:00:00"), "-34.64536566775859", "58.56192234666206" ));

        List<Reserva> reservas = List.of(
                new Reserva(null, null, null, "2024-05-05", "04:00", "06:00"),
                new Reserva(null, null, null, "2024-05-05", "20:00", "23:00"),
                new Reserva(null, null, null, "2024-05-05", "10:00", "12:00")
        );

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioReserva.obtenerReservasByUserId(usuario.getId())).thenReturn(reservas);

        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);

        when(servicioGarage.traerTodos()).thenReturn(garage);

        ModelAndView modelAndView = controladorReserva.listarReservas(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
    }

    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadasAunqueLaListaEsteVacia() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);

        List<Garage> garage = List.of(new Garage(null, "Suipacha", 5, LocalTime.parse("10:00:00"), LocalTime.parse("17:00:00"), "-34.64536566775859", "58.56192234666206"));

        List<Reserva> reservas = Collections.emptyList();

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioReserva.obtenerReservasByUserId(usuario.getId())).thenReturn(reservas);

        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);

        when(servicioGarage.traerTodos()).thenReturn(garage);

        ModelAndView modelAndView = controladorReserva.listarReservas(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
    }

    @Test
    public void listaDeReservaDebeLlevarteALoginSiNoHayUsuarioLogueado() {
        List<Reserva> reservas = Collections.emptyList();

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(null);

        ModelAndView modelAndView = controladorReserva.listarReservas(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }
*/
}
