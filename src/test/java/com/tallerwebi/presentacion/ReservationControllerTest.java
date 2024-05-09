package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.dominio.ReservationService;
import com.tallerwebi.dominio.UserService;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class ReservationControllerTest {

    private UserService userService;
    private GarageService garageService;
    private ReservationService reservationService;
    private ReservationController reservationController;

    @BeforeEach
    public void init(){
        userService = mock(UserService.class);
        garageService = mock(GarageService.class);
        reservationService = mock(ReservationService.class);
        reservationController = new ReservationController(userService, garageService, reservationService);
    }

}
