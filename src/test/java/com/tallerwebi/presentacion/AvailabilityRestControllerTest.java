package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AvailabilityRestControllerTest {

	private ControladorRestReservado controladorRestReservado;
	private String dateDtoMock;
	private ServicioRepositorio servicioRepositorioMock;


	@BeforeEach
	public void init(){
		dateDtoMock = "2024-07-05";
		servicioRepositorioMock = mock(ServicioRepositorio.class);
		controladorRestReservado = new ControladorRestReservado(servicioRepositorioMock);
	}

	@Test
	public void cuandoSeEligeUnaFechaQueNoTieneHorasOcupadasDebeTraerUnArrayVacio(){
		// preparacion
		when(servicioRepositorioMock.traerHorasOcupadas(anyString())).thenReturn(new ArrayList());

		// ejecucion
		ResponseEntity<List<String>> responseEntity = controladorRestReservado.traerDisponibilidad(dateDtoMock);

		// validacion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().isEmpty());
	}

	@Test
	public void cuandoSeEligeUnaFechaQueTieneElCupoOcupadoDeberiaDevolverUnArrayDeLasHorasOcupadas(){
		List<Integer> hours = Arrays.asList(10, 11, 20, 21);
		// preparacion
		when(servicioRepositorioMock.traerHorasOcupadas(anyString())).thenReturn(hours);

		// ejecucion
		ResponseEntity<List<String>> responseEntity = controladorRestReservado.traerDisponibilidad(dateDtoMock);

		// validacion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getBody(), hours);
	}

}
