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
    public void queAlEjecutarTraerTodosDebeDevolverLaListaCorrectaDeGarages(){
        List<Garage> garagesMock = new ArrayList<>();
        garagesMock.add(new Garage(1, "Gurruchaga", 20, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234", "foto.jpg"));
        garagesMock.add(new Garage(2, "Corrientes", 30, LocalTime.of(10,0), LocalTime.of(22,0), "5678", "-5678", "foto2.jpg"));
        when(repositorioGarage.findAll()).thenReturn(garagesMock);

        List<Garage> garages = servicioGarage.traerTodos();

        assertThat(garages, is(notNullValue()));
        assertThat(garages, hasSize(2));
        assertThat(garages, containsInAnyOrder(garagesMock.toArray(new Garage[0])));
    }

    @Test
    public void queAlBuscarPorIdDebeDevolverElGarageCorrecto(){
        int idBuscado = 1;
        Garage garageMock = new Garage(idBuscado, "Gurruchaga", 20, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234", "foto.jpg");
        when(repositorioGarage.findById(idBuscado)).thenReturn(garageMock);

        Garage garage = servicioGarage.buscarPorId(idBuscado);

        assertThat(garage, is(notNullValue()));
        assertThat(garage.getId(), equalTo(idBuscado));
    }

    @Test
    public void queAlBuscarGarageConCapacidadIgualAVeinteVehiculosDevuelvaLosGaragesCorrespondientes(){
        List<Garage> garagesMock = new ArrayList<>();
       Integer capacidadBuscada= 20;
        garagesMock.add(new Garage(null, "Gurruchaga", 20, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234", "foto.jpg"));
        garagesMock.add(new Garage(null, "Gurruchaga", 20, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234", "foto.jpg"));

        when(this.repositorioGarage.getGarageSegunCapacidad(capacidadBuscada)).thenReturn(garagesMock);


        List<Garage> garages = this.servicioGarage.getGaragesPorCapacidad(capacidadBuscada);

        assertThat(garages, is(notNullValue()));
        assertThat(garages, everyItem(hasProperty("capacidad", is(capacidadBuscada))));
        assertThat(garagesMock.size(), equalTo(2));
        assertThat(garages, everyItem(hasProperty("capacidad", greaterThanOrEqualTo(20))));
    }

    @Test
    public void queAlBuscarGarageConCapacidadIgualOMayorATreintaVehiculosDevuelvaLosGaragesCorrespondientes(){
        List<Garage> garagesMock = new ArrayList<>();
        Integer capacidadBuscada= 30;
        garagesMock.add(new Garage(null, "Gurruchaga", 30, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234", "foto.jpg"));
        garagesMock.add(new Garage(null, "Gurruchaga", 45, LocalTime.of(9,30), LocalTime.of(23,45), "1234", "-1234", "foto.jpg"));

        when(this.repositorioGarage.getGarageSegunCapacidad(capacidadBuscada)).thenReturn(garagesMock);

        List<Garage> garages = this.servicioGarage.getGaragesPorCapacidad(capacidadBuscada);

        assertThat(garages, is(notNullValue()));
        assertThat(garages, everyItem(hasProperty("capacidad", greaterThanOrEqualTo(30))));
    }
}
