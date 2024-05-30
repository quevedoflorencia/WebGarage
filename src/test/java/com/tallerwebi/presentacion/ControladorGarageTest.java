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

public class ControladorGarageTest {

    private ControladorGarage controladorGarage;
    private ServicioGarage servicioGarage;

    @BeforeEach
    public void init(){
        this.servicioGarage= mock(ServicioGarage.class);
        this.controladorGarage = new ControladorGarage(this.servicioGarage);
    }

    @Test
    public void queAlSolicitarLaPantallaDeListarGarageSeMuestreLaVistaListarGaragesConUnaListaVacia(){
        List<Garage> garages = Collections.emptyList();

        when(servicioGarage.traerTodos()).thenReturn(garages);

        ModelAndView mav = this.controladorGarage.listarGarages();

        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages"));
    }

    @Test
    public void queAlIngresarALaPantallaDeListarGaragesMeMuestreTodosLosGaragesExistentes(){
        List<Garage> garagesMock = new ArrayList<>();

        garagesMock.add(new Garage());
        garagesMock.add(new Garage());

        when(this.servicioGarage.traerTodos()).thenReturn(garagesMock);

        ModelAndView mav = this.controladorGarage.listarGarages();

        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages"));
        assertThat(mav.getModel().get("garages"), equalToObject(garagesMock));
    }
}
