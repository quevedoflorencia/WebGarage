package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioGarageTest {

    private ServicioGarage servicioGarage;

    private RepositorioGarage repositorioGarage;
    private RepositorioCalificacion repositorioCalificacion;

    @BeforeEach
    public void init(){
        this.repositorioGarage = mock(RepositorioGarage.class);
        this.repositorioCalificacion = mock(RepositorioCalificacion.class);
        this.servicioGarage = new ServicioGarageImpl(this.repositorioGarage, this.repositorioCalificacion);
    }

    @Test
    public void queAlEjecutarTraerTodosDebeDevolverLaListaCorrectaDeGarages() {
        List<Garage> garagesMock = Arrays.asList(
                new Garage(1, "Gurruchaga", LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto.jpg", null),
                new Garage(2, "Corrientes", LocalTime.of(10, 0), LocalTime.of(22, 0), "5678", "-5678", "foto2.jpg", null)
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
        Garage garageMock = new Garage(idBuscado, "Gurruchaga", LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto.jpg", null);
        when(repositorioGarage.findById(idBuscado)).thenReturn(garageMock);

        Garage garage = servicioGarage.buscarPorId(idBuscado);

        assertThat(garage, is(notNullValue()));
        assertThat(garage.getId(), equalTo(idBuscado));
    }

    @Test
    public void queLaPaginacionDevuelvaLosGaragesCorrectos() {
        List<Garage> garagesMock = Arrays.asList(
                new Garage(0, "Garage 0", LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto0.jpg", null),
                new Garage(1, "Garage 1", LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto1.jpg", null),
                new Garage(2, "Garage 2", LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto2.jpg", null)
        );

        Integer page = 1;
        Integer size = 3;

        when(repositorioGarage.obtenerPaginacion(page, size, null, null, null)).thenReturn(garagesMock.subList(0, 3));
        when(repositorioGarage.findAll()).thenReturn(garagesMock);

        List<Garage> paginados = servicioGarage.getPaginacion(page, size,  null, null, null);

        assertThat(paginados, is(notNullValue()));
        assertThat(paginados, hasSize(size));
        assertThat(paginados, containsInAnyOrder(garagesMock.subList(0, 3).toArray(new Garage[0])));
    }

    @Test
    public void queCalculeTotalPaginasCorrectamente() {
        Integer totalGarages = 10;
        Integer size = 3;

        Integer totalPages = servicioGarage.calcularTotalPaginas(totalGarages, size);

        assertThat(totalPages, is(4));
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
                new Garage(1, "Garage 1", LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto1.jpg", null),
                new Garage(2, "Garage 2", LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto2.jpg", null),
                new Garage(3, "Garage 3", LocalTime.of(9, 30), LocalTime.of(23, 45), "1234", "-1234", "foto3.jpg", null)
        );
        int page = 1;
        int size = 3;
        when(repositorioGarage.obtenerPaginacion(page, size, null, null, null)).thenReturn(garagesMock.subList(0, size));
        when(repositorioGarage.findAll()).thenReturn(garagesMock);

        List<Garage> paginados = servicioGarage.getPaginacion(page, size, null, null, null);

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
