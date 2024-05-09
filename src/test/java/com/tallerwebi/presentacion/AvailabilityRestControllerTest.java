package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ReservedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AvailabilityRestControllerTest {

	private ReservedRestController reservedRestController;
	private String dateDtoMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ReservedService reservedServiceMock;


	@BeforeEach
	public void init(){
		dateDtoMock = "2024-07-05";
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		reservedServiceMock = mock(ReservedService.class);
		reservedRestController = new ReservedRestController(reservedServiceMock);
	}

	@Test
	public void cuandoSeEligeUnaFechaQueNoTieneHorasOcupadasDebeTraerUnArrayVacio(){
		// preparacion
		when(reservedServiceMock.getReservedHours(anyString())).thenReturn(new ArrayList());

		// ejecucion
		ResponseEntity<List<String>> responseEntity = reservedRestController.getAvailableHours(dateDtoMock);

		// validacion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().isEmpty());
	}

	@Test
	public void cuandoSeEligeUnaFechaQueTieneElCupoOcupadoDeberiaDevolverUnArrayDeLasHorasOcupadas(){
		List<Integer> hours = Arrays.asList(10, 11, 20, 21);
		// preparacion
		when(reservedServiceMock.getReservedHours(anyString())).thenReturn(hours);

		// ejecucion
		ResponseEntity<List<String>> responseEntity = reservedRestController.getAvailableHours(dateDtoMock);

		// validacion
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertFalse(responseEntity.getBody().isEmpty());
	}

}
