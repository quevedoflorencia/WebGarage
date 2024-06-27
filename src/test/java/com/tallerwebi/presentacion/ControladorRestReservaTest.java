package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.model.Garage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorRestReservaTest {

	private ControladorRestReserva controladorRestReserva;
	private String dateDtoMock;
	private Integer garageTipoVehiculoId;
	private Integer garageId;

	private ServicioReserva servicioReservaMock;


	@BeforeEach
	public void init(){
		dateDtoMock = "2024-07-05";
		garageTipoVehiculoId = 1;
		servicioReservaMock = mock(ServicioReserva.class);
		controladorRestReserva = new ControladorRestReserva(servicioReservaMock);
	}

	@Test
	public void cuandoSeEligeUnaFechaQueNoTieneHorasOcupadasDebeTraerUnArrayVacio(){
		// preparacion
		when(servicioReservaMock.traerHorasOcupadas(anyString())).thenReturn(new ArrayList());

		// Crear el requestBody
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("selectedDate", dateDtoMock);
		requestBody.put("garageTipoVehiculoId", garageTipoVehiculoId);
		requestBody.put("garageId", garageId);

		// ejecucion
		ResponseEntity<List<String>> responseEntity = controladorRestReserva.traerDisponibilidad(requestBody);

		// validacion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().isEmpty());
	}

	@Test
	public void cuandoSeEligeUnaFechaQueTieneElCupoOcupadoDeberiaDevolverUnArrayDeLasHorasOcupadas(){
		List<String> horas = Collections.emptyList();
		List<String> horasOcupadas = IntStream.rangeClosed(9,22).mapToObj(Integer::toString).collect(Collectors.toList());

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("selectedDate", dateDtoMock);
		requestBody.put("garageTipoVehiculoId", garageTipoVehiculoId);
		requestBody.put("garageId", garageId);

		when(servicioReservaMock.traerHorasOcupadasPorDiaYTipoVehiculo(dateDtoMock, garageTipoVehiculoId)).thenReturn(horas);
		when(servicioReservaMock.traerHorasCierre(garageId)).thenReturn(horasOcupadas);

		ResponseEntity<List<String>> responseEntity = controladorRestReserva.traerDisponibilidad(requestBody);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getBody(), horasOcupadas);
	}

	@Test
	public void cuandoElServicioTraeUnErrorInternoYDevuelveUnaExcepcion(){
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("selectedDate", dateDtoMock);
		requestBody.put("garageTipoVehiculoId", garageTipoVehiculoId);
		requestBody.put("garageId", garageId);

		doThrow(new RuntimeException()).when(servicioReservaMock).traerHorasOcupadasPorDiaYTipoVehiculo(dateDtoMock, garageTipoVehiculoId);

		ResponseEntity<List<String>> responseEntity = controladorRestReserva.traerDisponibilidad(requestBody);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	}

}
