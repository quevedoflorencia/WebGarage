package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioGarage;
import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.model.Garage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorGarageTest {
    private ControladorGarage controladorGarage;
    private ServicioGarage servicioGarage;
    private RepositorioGarage repositorioGarage;

    @BeforeEach
    public void init(){
        this.servicioGarage= mock(ServicioGarage.class);
        this.repositorioGarage= mock(RepositorioGarage.class);
        this.controladorGarage = new ControladorGarage(this.servicioGarage, this.repositorioGarage);
    }

    @Test
    public void queAlSolicitarLaPantallaDeListarGarageSeMuestreLaVistaListarGarages(){
        // preparacion

        // ejecucion
        ModelAndView mav = this.controladorGarage.listarGarages();

        // verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages")); // Vista correcta

    }
    /*
    @Test
    public void queAlIngresarALaPantallaDeListarGaragesMeMuestreTodosLosGaragesExistentes(){
        // preparacion
        List<Garage> garagesMock = new ArrayList<>();
        LocalTime horarioApertura= LocalTime.of(8,0);
        LocalTime horarioCierre= LocalTime.of(23,0);
        garagesMock.add(new Garage(null, "Sarmiento", 5, horarioApertura, horarioCierre, "1234", "-1234"));

        garagesMock.add(new Garage(null, "Gurruchaga", 22, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234"));
        when(this.servicioGarage.traerTodos()).thenReturn(garagesMock);

        // ejecucion
        ModelAndView mav = this.controladorGarage.listarGarages();

        // verificacion
        List<Garage> garagesListMock = (List<Garage>) mav.getModel().get("garages");
        assertThat(garagesMock.size(), equalTo(2));
    }

*/
}
