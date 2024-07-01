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
import static org.hamcrest.Matchers.equalTo;
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
    public void queAlSolicitarLaPantallaDeListarGarageSeMuestreLaVistaListarGaragesConUnaListaVacia() {
        List<Garage> garages = Collections.emptyList();

        when(servicioGarage.getPaginacion(1, 3)).thenReturn(garages);
        when(servicioGarage.traerTodos()).thenReturn(garages);

        ModelAndView mav = controladorGarage.inicio(1, 3);

        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages"));
        assertThat((List<Garage>) mav.getModel().get("garages"), equalTo(garages));
    }

    @Test
    public void queSeCalculeCorrectamenteElNumeroDePaginas() {
        List<Garage> garagesMock = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            garagesMock.add(new Garage());
        }

        when(servicioGarage.getPaginacion(1, 3)).thenReturn(garagesMock.subList(0, 3));
        when(servicioGarage.traerTodos()).thenReturn(garagesMock);
        when(servicioGarage.validarPagina(null)).thenReturn(1);
        when(servicioGarage.validarTamanioPagina(null)).thenReturn(3);
        when(servicioGarage.calcularTotalPaginas(10, 3)).thenReturn(4);
        when(servicioGarage.generarNumerosPagina(4)).thenReturn(List.of(1, 2, 3, 4));

        ModelAndView mav = this.controladorGarage.inicio(null, null);
        System.out.println("currentPage: " + mav.getModel().get("currentPage"));

        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages"));
        assertThat(mav.getModel().get("currentPage"), equalTo(1));
        assertThat(mav.getModel().get("pageSize"), equalTo(3));

        List<Integer> expectedPageNumbers = List.of(1, 2, 3, 4);
        assertThat((List<Integer>) mav.getModel().get("pageNumbers"), equalTo(expectedPageNumbers));
    }

}
