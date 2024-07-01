package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.*;

public class ControladorRestPdfTest {

    private ServicioReserva servicioReserva;
    private ControladorRestPdf controladorRestPdf;

    @BeforeEach
    public void init() {
        servicioReserva = mock(ServicioReserva.class);
        controladorRestPdf = new ControladorRestPdf(servicioReserva);
    }

    @Test
    public void generatePdf_ReservaFound() throws IOException {
        Long reservaID = 1L;
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "USER", true);
        Garage garage = new Garage(1, "Garage 1", 50, null, null, "0.0", "0.0", "rutaFoto.jpg");
        Reserva reserva = new Reserva(usuario, garage, new GarageTipoVehiculo(), "2024-05-05", "10:00", "12:00", 100.0, null);

        String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        when(servicioReserva.buscarPorId(reservaID)).thenReturn(reserva);

        MockHttpServletResponse response = new MockHttpServletResponse();
        controladorRestPdf.generatePdf(reservaID, response);

        assertThat(response.getContentType(), equalTo("application/pdf"));
        assertThat(response.getHeader("Content-Disposition"), equalTo("attachment; filename="+fechaActual+".pdf"));
        assertThat(response.getContentAsByteArray().length, greaterThan(0));

        PDDocument document = PDDocument.load(response.getContentAsByteArray());
        assertThat(document.getNumberOfPages(), greaterThan(0));
        document.close();
    }

    @Test
    public void generatePdf_ReservaNotFound() throws IOException {
        Long reservaID = 1L;
        String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        when(servicioReserva.buscarPorId(reservaID)).thenReturn(null);

        MockHttpServletResponse response = new MockHttpServletResponse();
        controladorRestPdf.generatePdf(reservaID, response);

        assertThat(response.getContentType(), equalTo("application/pdf"));
        assertThat(response.getHeader("Content-Disposition"), equalTo("attachment; filename="+fechaActual+".pdf"));
        assertThat(response.getContentAsByteArray().length, greaterThan(0));

        PDDocument document = PDDocument.load(response.getContentAsByteArray());
        assertThat(document.getNumberOfPages(), greaterThan(0));
        document.close();
    }

    @Test
    public void generatePdf_ThrowsIOException() throws IOException {
        Long reservaID = 1L;
        Usuario usuario = new Usuario(1L, "Test", "test@unlam.edu.ar", "test", "USER", true);
        Garage garage = new Garage(1, "Garage 1", 50, null, null, "0.0", "0.0", "rutaFoto.jpg");
        Reserva reserva = new Reserva(usuario, garage, new GarageTipoVehiculo(), "2024-05-05", "10:00", "12:00", 100.0, new EstadoReserva("Confirmada"));

        when(servicioReserva.buscarPorId(reservaID)).thenReturn(reserva);

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Simular IOException al cerrar el documento
        PDDocument documentMock = mock(PDDocument.class);
        doThrow(new IOException("Simulated IOException")).when(documentMock).close();

        try {
            controladorRestPdf.generatePdf(reservaID, response);
        } catch (IOException e) {
            assertThat(e.getMessage(), equalTo("Simulated IOException"));
        }
    }
}
