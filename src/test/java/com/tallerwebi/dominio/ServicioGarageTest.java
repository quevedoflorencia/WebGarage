package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioGarageTest {

    private ServicioGarage servicioGarage;
    private RepositorioGarage repositorioGarage;

    @BeforeEach
    public void init(){
        this.repositorioGarage = mock(RepositorioGarage.class);
        this.servicioGarage = new ServicioGarageImpl(this.repositorioGarage);
    }

    @Test
    public void queAlBuscarGarageConCapacidadIgualAVeinteVehiculosDevuelvaLosGaragesCorrespondientes(){
        // preparacion
        List<Garage> garagesMock = new ArrayList<>();
       Integer capacidadBuscada= 20;
        garagesMock.add(new Garage(null, "Gurruchaga", 20, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234"));
        garagesMock.add(new Garage(null, "Gurruchaga", 20, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234"));
        //garagesMock.add(new Garage(null, "Gurruchaga", 77, LocalTime.of(9,30), LocalTime.of(23,45)));
        when(this.repositorioGarage.getGarageSegunCapacidad(capacidadBuscada)).thenReturn(garagesMock);

        // ejecucion
        List<Garage> garages = this.servicioGarage.getGaragesPorCapacidad(capacidadBuscada);

        // verificacion
        assertThat(garages, is(notNullValue()));
        assertThat(garages, everyItem(hasProperty("capacidad", is(capacidadBuscada))));
        assertThat(garagesMock.size(), equalTo(2));
        assertThat(garages, everyItem(hasProperty("capacidad", greaterThanOrEqualTo(20))));
    }

    @Test
    public void queAlBuscarGarageConCapacidadIgualOMayorATreintaVehiculosDevuelvaLosGaragesCorrespondientes(){
        // preparacion
        List<Garage> garagesMock = new ArrayList<>();
        Integer capacidadBuscada= 30;
        garagesMock.add(new Garage(null, "Gurruchaga", 30, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234"));
        garagesMock.add(new Garage(null, "Gurruchaga", 45, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234"));
        //garagesMock.add(new Garage(null, "Gurruchaga", 77, LocalTime.of(9,30), LocalTime.of(23,45)));
        when(this.repositorioGarage.getGarageSegunCapacidad(capacidadBuscada)).thenReturn(garagesMock);

        // ejecucion
        List<Garage> garages = this.servicioGarage.getGaragesPorCapacidad(capacidadBuscada);

        // verificacion
        assertThat(garages, is(notNullValue()));
        assertThat(garages, everyItem(hasProperty("capacidad", greaterThanOrEqualTo(30))));
    }





}
