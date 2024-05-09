package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.dominio.ReservationService;
import com.tallerwebi.dominio.UserService;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.dominio.model.User;
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

public class ReservationControllerTest {

    private UserService userService;
    private GarageService garageService;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ReservationService reservationService;
    private ReservationController reservationController;

    @BeforeEach
    public void init(){
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        userService = mock(UserService.class);
        garageService = mock(GarageService.class);
        reservationService = mock(ReservationService.class);
        reservationController = new ReservationController(userService, garageService, reservationService);
    }


    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadas() {
        User user = new User(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);

        List<Garage> garage = List.of(new Garage(null, "Suipacha", 5, LocalTime.parse("10:00:00"), LocalTime.parse("17:00:00")));

        List<Reservation> reservations = List.of(
                new Reservation(null, null, null, "2024-05-05", "04:00", "06:00"),
                new Reservation(null, null, null, "2024-05-05", "20:00", "23:00"),
                new Reservation(null, null, null, "2024-05-05", "10:00", "12:00")
        );

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(user.getId());
        when(reservationService.obtenerReservasByUserId(user.getId())).thenReturn(reservations);

        when(userService.get(user.getId())).thenReturn(user);

        when(garageService.getAll()).thenReturn(garage);

        ModelAndView modelAndView = reservationController.listReservation(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
    }

    @Test
    public void listaDeReservaDebeMostrarLaVistaConTodasMisReservasRealizadasAunqueLaListaEsteVacia() {
        User user = new User(1L, "Test", "test@unlam.edu.ar", "test", "ADMIN", true);

        List<Garage> garage = List.of(new Garage(null, "Suipacha", 5, LocalTime.parse("10:00:00"), LocalTime.parse("17:00:00")));

        List<Reservation> reservations = Collections.emptyList();

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(user.getId());
        when(reservationService.obtenerReservasByUserId(user.getId())).thenReturn(reservations);

        when(userService.get(user.getId())).thenReturn(user);

        when(garageService.getAll()).thenReturn(garage);

        ModelAndView modelAndView = reservationController.listReservation(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("my-reservation"));
    }

    @Test
    public void listaDeReservaDebeLlevarteALoginSiNoHayUsuarioLogueado() {
        List<Reservation> reservations = Collections.emptyList();

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("ID")).thenReturn(null);

        ModelAndView modelAndView = reservationController.listReservation(requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

}
