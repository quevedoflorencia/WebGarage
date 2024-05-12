package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.ServicioRepositorio;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reservacion;
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

public class ReservacionControllerTest {

    private ServicioUsuario servicioUsuario;
    private ServicioGarage servicioGarage;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ServicioRepositorio servicioRepositorio;
    private ControladorReservaciones controladorReservaciones;

    @BeforeEach
    public void init(){
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioUsuario = mock(ServicioUsuario.class);
        servicioGarage = mock(ServicioGarage.class);
        servicioRepositorio = mock(ServicioRepositorio.class);
        controladorReservaciones = new ControladorReservaciones(servicioUsuario, servicioGarage, servicioRepositorio);
    }


    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadas() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);

        List<Garage> garage = List.of(new Garage(null, "Suipacha", 5, LocalTime.parse("10:00:00"), LocalTime.parse("17:00:00")));

        List<Reservacion> reservacions = List.of(
                new Reservacion(null, null, null, "2024-05-05", "04:00", "06:00"),
                new Reservacion(null, null, null, "2024-05-05", "20:00", "23:00"),
                new Reservacion(null, null, null, "2024-05-05", "10:00", "12:00")
        );

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioRepositorio.obtenerReservasByUserId(usuario.getId())).thenReturn(reservacions);

        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);

        when(servicioGarage.getAll()).thenReturn(garage);

        ModelAndView modelAndView = controladorReservaciones.listReservation(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
    }

    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadasAunqueLaListaEsteVacia() {
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);

        List<Garage> garage = List.of(new Garage(null, "Suipacha", 5, LocalTime.parse("10:00:00"), LocalTime.parse("17:00:00")));

        List<Reservacion> reservacions = Collections.emptyList();

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(usuario.getId());
        when(servicioRepositorio.obtenerReservasByUserId(usuario.getId())).thenReturn(reservacions);

        when(servicioUsuario.get(usuario.getId())).thenReturn(usuario);

        when(servicioGarage.getAll()).thenReturn(garage);

        ModelAndView modelAndView = controladorReservaciones.listReservation(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
    }

    @Test
    public void listaDeReservaDebeLlevarteALoginSiNoHayUsuarioLogueado() {
        List<Reservacion> reservacions = Collections.emptyList();

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(null);

        ModelAndView modelAndView = controladorReservaciones.listReservation(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

}
