package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.model.Garage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorHomeTest {

    private ControladorHome controladorHome;
    private ServicioGarage servicioGarage;

    @BeforeEach
    public void init(){
        this.servicioGarage= mock(ServicioGarage.class);
        this.controladorHome = new ControladorHome(this.servicioGarage);
    }

    @Test
    public void homeDeberiaTraerLaPaginaHomeConUnaListaDeGaragesVacia() {
        List<Garage> garages = Collections.emptyList();

        when(servicioGarage.getPaginacion(1, 10, false, null)).thenReturn(garages);

        ModelAndView mav = this.controladorHome.inicio();

        assertThat(mav.getViewName(), equalToIgnoringCase("home"));
    }

    /*@Test
    public void homeDeberiaTraerLaPaginaHomeConLaListaDeGaragesExistentes() {
        List<Garage> garagesMock = new ArrayList<>();

        garagesMock.add(new Garage());
        garagesMock.add(new Garage());

        when(this.servicioGarage.getPaginacion(1, 10, false)).thenReturn(garagesMock);

        ModelAndView mav = this.controladorHome.inicio();

        assertThat(mav.getViewName(), equalToIgnoringCase("home"));
        assertThat(mav.getModel().get("garages"), equalToObject(garagesMock));
    }*/

    @Test
    public void laRutaPorDefectoDeberiaSerHomeComoPaginaPrincipal() {
        ModelAndView mav = this.controladorHome.irAHome();
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/"));
    }
}
