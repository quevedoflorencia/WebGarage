package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCalificacion;
import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.ServicioTipoVehiculo;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.TipoVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorGarageTest {

    private ControladorGarage controladorGarage;
    private ServicioGarage servicioGarage;
    private ServicioTipoVehiculo servicioTipoVehiculo;
    private ServicioCalificacion servicioCalificacion;

    @BeforeEach
    public void init() {
        this.servicioGarage = mock(ServicioGarage.class);
        this.servicioTipoVehiculo = mock(ServicioTipoVehiculo.class);
        this.servicioCalificacion = mock(ServicioCalificacion.class);
        this.controladorGarage = new ControladorGarage(this.servicioGarage, this.servicioTipoVehiculo, this.servicioCalificacion);
    }

    @Test
    public void queAlSolicitarLaPantallaDeListarGarageSeMuestreLaVistaListarGaragesConUnaListaVacia() {
        List<Garage> garages = Collections.emptyList();

        when(servicioGarage.getPaginacion(1, 3,false, null)).thenReturn(garages);
        when(servicioGarage.traerTodos()).thenReturn(garages);

        ModelAndView mav = controladorGarage.inicio(1, 3, null, false, null);

        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages"));
        assertThat((List<Garage>) mav.getModel().get("garages"), equalTo(garages));
    }

    @Test
    public void queSeCalculeCorrectamenteElNumeroDePaginas() {
        List<Garage> garagesMock = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            garagesMock.add(new Garage());
        }

        when(servicioGarage.getPaginacion(1, 3, false, null)).thenReturn(garagesMock.subList(0, 3));
        when(servicioGarage.traerTodos()).thenReturn(garagesMock);
        when(servicioGarage.validarPagina(null)).thenReturn(1);
        when(servicioGarage.validarTamanioPagina(null)).thenReturn(3);
        when(servicioGarage.calcularTotalPaginas(10, 3)).thenReturn(4);
        when(servicioGarage.generarNumerosPagina(4)).thenReturn(List.of(1, 2, 3, 4));

        ModelAndView mav = this.controladorGarage.inicio(null, null, null, false, null);

        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages"));
        assertThat(mav.getModel().get("currentPage"), equalTo(1));
        assertThat(mav.getModel().get("pageSize"), equalTo(3));

        List<Integer> expectedPageNumbers = List.of(1, 2, 3, 4);
        assertThat((List<Integer>) mav.getModel().get("pageNumbers"), equalTo(expectedPageNumbers));
    }

    @Test
    public void queAlFiltrarPorTipoVehiculoSeMuestreLaVistaCorrecta() {
        List<Garage> garages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            garages.add(new Garage());
        }

        when(servicioGarage.getPaginacion(1, 3, false, null)).thenReturn(garages.subList(0, 3));
        when(servicioGarage.obtenerGaragesPorTipoVehiculo(1)).thenReturn(garages);
        when(servicioGarage.validarPagina(1)).thenReturn(1);
        when(servicioGarage.validarTamanioPagina(3)).thenReturn(3);
        when(servicioGarage.calcularTotalPaginas(5, 3)).thenReturn(2);
        when(servicioGarage.generarNumerosPagina(2)).thenReturn(List.of(1, 2));

        ModelAndView mav = this.controladorGarage.inicio(1, 3, "1", false, null);

        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages"));
        assertThat(mav.getModel().get("currentPage"), equalTo(1));
        assertThat(mav.getModel().get("pageSize"), equalTo(3));
        assertThat(mav.getModel().get("tipoVehiculoSeleccionado"), equalTo(1));
        assertThat((List<Integer>) mav.getModel().get("pageNumbers"), equalTo(List.of(1, 2)));
    }

    @Test
    public void queSeMuestreListadoDeTiposDeVehiculos() {
        List<TipoVehiculo> tiposVehiculos = new ArrayList<>();
        TipoVehiculo tipoVehiculo = new TipoVehiculo();
        tipoVehiculo.setDescripcion("Bicicleta");
        tiposVehiculos.add(tipoVehiculo);

        when(servicioGarage.getPaginacion(1, 3, false, null)).thenReturn(Collections.emptyList());
        when(servicioGarage.traerTodos()).thenReturn(Collections.emptyList());
        when(servicioTipoVehiculo.traerTodos()).thenReturn(tiposVehiculos);

        ModelAndView mav = this.controladorGarage.inicio(1, 3, null, false, null);

        assertThat(mav.getViewName(), equalToIgnoringCase("listar-garages"));
        assertThat((List<TipoVehiculo>) mav.getModel().get("tipoVehiculos"), equalTo(tiposVehiculos));
    }
}
