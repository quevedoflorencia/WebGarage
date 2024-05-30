package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorRestReservaTest {

	private ControladorRestReserva controladorRestReserva;
	private String dateDtoMock;
	private ServicioReserva servicioReservaMock;


	@BeforeEach
	public void init(){
		dateDtoMock = "2024-07-05";
		servicioReservaMock = mock(ServicioReserva.class);
		controladorRestReserva = new ControladorRestReserva(servicioReservaMock);
	}

	@Test
	public void cuandoSeEligeUnaFechaQueNoTieneHorasOcupadasDebeTraerUnArrayVacio(){
		// preparacion
		when(servicioReservaMock.traerHorasOcupadas(anyString())).thenReturn(new ArrayList());

		// ejecucion
		ResponseEntity<List<String>> responseEntity = controladorRestReserva.traerDisponibilidad(dateDtoMock);

		// validacion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().isEmpty());
	}

	@Test
	public void cuandoSeEligeUnaFechaQueTieneElCupoOcupadoDeberiaDevolverUnArrayDeLasHorasOcupadas(){
		List<Integer> hours = Arrays.asList(10, 11, 20, 21);
		// preparacion
		when(servicioReservaMock.traerHorasOcupadas(anyString())).thenReturn(hours);

		// ejecucion
		ResponseEntity<List<String>> responseEntity = controladorRestReserva.traerDisponibilidad(dateDtoMock);

		// validacion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getBody(), hours);
	}

	@Test
	public void cuandoElServicioTraeUnErrorInternoYDevuelveUnaExcepcion(){
		doThrow(new RuntimeException()).when(servicioReservaMock).traerHorasOcupadas(anyString());

		ResponseEntity<List<String>> responseEntity = controladorRestReserva.traerDisponibilidad(dateDtoMock);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	}

}
