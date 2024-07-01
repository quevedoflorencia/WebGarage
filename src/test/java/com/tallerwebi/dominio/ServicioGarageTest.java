package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void queAlEjecutarTraerTodosDebeDevolverLaListaCorrectaDeGarages() {
        List<Garage> garagesMock = Arrays.asList(
                new Garage(1, "Gurruchaga", 20, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto.jpg"),
                new Garage(2, "Corrientes", 30, LocalTime.of(10, 0), LocalTime.of(22, 0), "5678", "-5678", "foto2.jpg")
        );
        when(repositorioGarage.findAll()).thenReturn(garagesMock);

        List<Garage> garages = servicioGarage.traerTodos();

        assertThat(garages, is(notNullValue()));
        assertThat(garages, hasSize(2));
        assertThat(garages, containsInAnyOrder(garagesMock.toArray(new Garage[0])));
    }

    @Test
    public void queAlBuscarPorIdDebeDevolverElGarageCorrecto() {
        int idBuscado = 1;
        Garage garageMock = new Garage(idBuscado, "Gurruchaga", 20, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto.jpg");
        when(repositorioGarage.findById(idBuscado)).thenReturn(garageMock);

        Garage garage = servicioGarage.buscarPorId(idBuscado);

        assertThat(garage, is(notNullValue()));
        assertThat(garage.getId(), equalTo(idBuscado));
    }

    @Test
    public void queAlBuscarGarageConCapacidadIgualAVeinteVehiculosDevuelvaLosGaragesCorrespondientes() {
        List<Garage> garagesMock = Arrays.asList(
                new Garage(null, "Gurruchaga", 20, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto.jpg"),
                new Garage(null, "Gurruchaga", 20, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto.jpg")
        );
        Integer capacidadBuscada = 20;
        when(this.repositorioGarage.getGarageSegunCapacidad(capacidadBuscada)).thenReturn(garagesMock);

        List<Garage> garages = this.servicioGarage.getGaragesPorCapacidad(capacidadBuscada);

        assertThat(garages, is(notNullValue()));
        assertThat(garages, everyItem(hasProperty("capacidad", is(capacidadBuscada))));
        assertThat(garages.size(), equalTo(2));
        assertThat(garages, everyItem(hasProperty("capacidad", greaterThanOrEqualTo(20))));
    }

    @Test
    public void queAlBuscarGarageConCapacidadIgualOMayorATreintaVehiculosDevuelvaLosGaragesCorrespondientes() {
        List<Garage> garagesMock = Arrays.asList(
                new Garage(null, "Ramos Mejia", 30, LocalTime.of(9, 0), LocalTime.of(23, 0), "1234", "-1234", "foto.jpg"),
                new Garage(null, "Caminito", 45, LocalTime.of(9, 0), LocalTime.of(23, 0), "1234", "-1234", "foto.jpg")
        );
        Integer capacidadBuscada = 30;
        when(this.repositorioGarage.getGarageSegunCapacidad(capacidadBuscada)).thenReturn(garagesMock);

        List<Garage> garages = this.servicioGarage.getGaragesPorCapacidad(capacidadBuscada);

        assertThat(garages, is(notNullValue()));
        assertThat(garages, everyItem(hasProperty("capacidad", greaterThanOrEqualTo(30))));
    }

    @Test
    public void queLaPaginacionDevuelvaLosGaragesCorrectos() {
        List<Garage> garagesMock = Arrays.asList(
                new Garage(0, "Garage 0", 20, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto0.jpg"),
                new Garage(1, "Garage 1", 21, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto1.jpg"),
                new Garage(2, "Garage 2", 22, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto2.jpg")
        );

        Integer page = 1;
        Integer size = 3;

        when(repositorioGarage.obtenerPaginacion(page, size)).thenReturn(garagesMock.subList(0, 3));
        when(repositorioGarage.findAll()).thenReturn(garagesMock);

        List<Garage> paginados = servicioGarage.getPaginacion(page, size);

        assertThat(paginados, is(notNullValue()));
        assertThat(paginados, hasSize(size));
        assertThat(paginados, containsInAnyOrder(garagesMock.subList(0, 3).toArray(new Garage[0])));
    }

    @Test
    public void queCalculeTotalPaginasCorrectamente() {
        Integer totalGarages = 10;
        Integer size = 3;

        Integer totalPages = servicioGarage.calcularTotalPaginas(totalGarages, size);

        assertThat(totalPages, is(4)); // 10 garages, 3 por página -> 4 páginas
    }

    @Test
    public void queGenereNumerosDePaginaCorrectamente() {
        Integer totalPages = 4;

        List<Integer> pageNumbers = servicioGarage.generarNumerosPagina(totalPages);

        assertThat(pageNumbers, is(notNullValue()));
        assertThat(pageNumbers, hasSize(totalPages));
        assertThat(pageNumbers, contains(1, 2, 3, 4));
    }

    @Test
    public void queValidePaginaCorrectamente() {
        Integer paginaInvalida = -1;
        Integer paginaValida = servicioGarage.validarPagina(paginaInvalida);

        assertThat(paginaValida, is(1));

        paginaInvalida = 0;
        paginaValida = servicioGarage.validarPagina(paginaInvalida);

        assertThat(paginaValida, is(1));
    }

    @Test
    public void queValideTamanioPaginaCorrectamente() {
        Integer tamanioInvalido = -1;
        Integer tamanioValido = servicioGarage.validarTamanioPagina(tamanioInvalido);

        assertThat(tamanioValido, is(3));

        tamanioInvalido = 0;
        tamanioValido = servicioGarage.validarTamanioPagina(tamanioInvalido);

        assertThat(tamanioValido, is(3));
    }

    @Test
    public void testGetPaginacion() {
        List<Garage> garagesMock = Arrays.asList(
                new Garage(1, "Garage 1", 21, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto1.jpg"),
                new Garage(2, "Garage 2", 22, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto2.jpg"),
                new Garage(3, "Garage 3", 23, LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto3.jpg")
        );
        int page = 1;
        int size = 3;
        when(repositorioGarage.obtenerPaginacion(page, size)).thenReturn(garagesMock.subList(0, size));
        when(repositorioGarage.findAll()).thenReturn(garagesMock);

        List<Garage> paginados = servicioGarage.getPaginacion(page, size);

        assertThat(paginados, hasSize(size));
        assertThat(paginados, containsInAnyOrder(garagesMock.subList(0, size).toArray(new Garage[0])));
    }

    @Test
    public void calcularTotalPaginas() {
        int totalGarages = 10;
        int size = 3;
        int expectedTotalPages = (int) Math.ceil((double) totalGarages / size);

        int totalPages = servicioGarage.calcularTotalPaginas(totalGarages, size);

        assertThat(totalPages, is(equalTo(expectedTotalPages)));
    }

    @Test
    public void generarNumerosPagina() {
        int totalPages = 4;
        List<Integer> expectedPageNumbers = Arrays.asList(1, 2, 3, 4);

        List<Integer> pageNumbers = servicioGarage.generarNumerosPagina(totalPages);

        assertThat(pageNumbers, is(expectedPageNumbers));
    }

    @Test
    public void validarPagina() {
        int paginaInvalida = -1;
        int paginaValidada = servicioGarage.validarPagina(paginaInvalida);

        assertThat(paginaValidada, is(1));

        paginaInvalida = 0;
        paginaValidada = servicioGarage.validarPagina(paginaInvalida);

        assertThat(paginaValidada, is(1));

        paginaInvalida = 5;
        paginaValidada = servicioGarage.validarPagina(paginaInvalida);

        assertThat(paginaValidada, is(5));
    }

    @Test
    public void validarTamanioPagina() {
        int tamanioInvalido = -1;
        int tamanioValidado = servicioGarage.validarTamanioPagina(tamanioInvalido);

        assertThat(tamanioValidado, is(3));

        tamanioInvalido = 0;
        tamanioValidado = servicioGarage.validarTamanioPagina(tamanioInvalido);

        assertThat(tamanioValidado, is(3));

        tamanioInvalido = 5;
        tamanioValidado = servicioGarage.validarTamanioPagina(tamanioInvalido);

        assertThat(tamanioValidado, is(5));
    }
}
