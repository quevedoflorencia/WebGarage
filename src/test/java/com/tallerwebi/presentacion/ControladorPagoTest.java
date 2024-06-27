package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EmailService;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.excepcion.ExcepcionCvvTarjetaInvalida;
import com.tallerwebi.dominio.excepcion.ExcepcionNumeroTarjetaInvalida;
import com.tallerwebi.dominio.excepcion.ExcepcionReservaNoExiste;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.presentacion.dto.DatosPagoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorPagoTest {

    private static final Long RESERVA_ID = 1L;
    private static final String NUMERO_TARJETA_VALIDO = "1234567890123456";
    private static final String CVV_VALIDO = "123";
    private static final String NUMERO_TARJETA_INVALIDO = "123";
    private static final String CVV_INVALIDO = "12";

    private ServicioPago servicioPago;
    private ServicioReserva servicioReserva;
    private EmailService emailService;
    private ControladorPago controladorPago;

    private DatosPagoDTO datosPagoDTO;
    private Reserva reserva;

    @BeforeEach
    public void init() {
        reserva = new Reserva();

        datosPagoDTO = new DatosPagoDTO();
        datosPagoDTO.setIdReserva(RESERVA_ID);
        datosPagoDTO.setNumeroTarjeta(NUMERO_TARJETA_VALIDO);
        datosPagoDTO.setCvv(CVV_VALIDO);

        this.servicioPago = mock(ServicioPago.class);
        this.servicioReserva = mock(ServicioReserva.class);
        this.emailService = mock(EmailService.class);
        this.controladorPago = new ControladorPago(servicioPago, servicioReserva, emailService);
    }

    @Test
    void irPagoDeberiaMostrarFormularioPago() {
        Long reservaId = 1L;
        ModelAndView mav = controladorPago.irPago(reservaId);

        assertThat(mav.getViewName(), equalToIgnoringCase("formulario-pago"));

        DatosPagoDTO datosPagoDTO = (DatosPagoDTO) mav.getModel().get("pagoData");
        assertThat(datosPagoDTO.getIdReserva(), is(reservaId));
    }

    @Test
    void validarPagoDeberiaMostrarPagoExitosoSiElPagoEsCorrecto() throws ExcepcionNumeroTarjetaInvalida, ExcepcionCvvTarjetaInvalida, ExcepcionReservaNoExiste {

        when(servicioReserva.buscarPorId(datosPagoDTO.getIdReserva())).thenReturn(reserva);

        ModelAndView mav = controladorPago.validarPago(datosPagoDTO);

        verify(servicioPago).validarTarjeta(datosPagoDTO.getNumeroTarjeta(), datosPagoDTO.getCvv());
        verify(servicioPago).registrarPago(reserva);

        assertThat(mav.getViewName(), equalToIgnoringCase("pago-exitoso"));
        assertThat(mav.getModel().get("exito"), is("¡Su pago ha sido exitoso, te esperamos!"));
        assertThat(mav.getModel().get("reserva"), equalToObject(reserva));
    }

    @Test
    void validarPagoDeberiaMostrarErrorSiLaReservaNoExiste() throws ExcepcionNumeroTarjetaInvalida, ExcepcionCvvTarjetaInvalida, ExcepcionReservaNoExiste {
        when(servicioReserva.buscarPorId(datosPagoDTO.getIdReserva())).thenReturn(null);

        ModelAndView mav = controladorPago.validarPago(datosPagoDTO);

        verify(servicioPago, never()).validarTarjeta(anyString(), anyString());
        verify(servicioPago, never()).registrarPago(reserva);

        assertThat(mav.getViewName(), equalToIgnoringCase("formulario-pago"));
        assertThat(mav.getModel().get("error"), is("Hubo un inconveniente, por favor intente nuevamente"));
    }

    @Test
    void validarPagoDeberiaMostrarErrorSiElNumeroDeTarjetaEsInvalido() throws ExcepcionNumeroTarjetaInvalida, ExcepcionCvvTarjetaInvalida, ExcepcionReservaNoExiste {

        datosPagoDTO.setNumeroTarjeta(NUMERO_TARJETA_INVALIDO);

        when(servicioReserva.buscarPorId(datosPagoDTO.getIdReserva())).thenReturn(reserva);
        doThrow(new ExcepcionNumeroTarjetaInvalida()).when(servicioPago).validarTarjeta(datosPagoDTO.getNumeroTarjeta(), datosPagoDTO.getCvv());

        ModelAndView mav = controladorPago.validarPago(datosPagoDTO);

        verify(servicioPago).validarTarjeta(datosPagoDTO.getNumeroTarjeta(), datosPagoDTO.getCvv());
        verify(servicioPago, never()).registrarPago(reserva);

        assertThat(mav.getViewName(), equalToIgnoringCase("formulario-pago"));
        assertThat(mav.getModel().get("error"), is("Número de Tarjeta inválida"));
    }

    @Test
    void validarPagoDeberiaMostrarErrorSiElCvvEsInvalido() throws ExcepcionNumeroTarjetaInvalida, ExcepcionCvvTarjetaInvalida, ExcepcionReservaNoExiste {

        datosPagoDTO.setCvv(CVV_INVALIDO);

        when(servicioReserva.buscarPorId(datosPagoDTO.getIdReserva())).thenReturn(reserva);
        doThrow(new ExcepcionCvvTarjetaInvalida()).when(servicioPago).validarTarjeta(datosPagoDTO.getNumeroTarjeta(), datosPagoDTO.getCvv());

        ModelAndView mav = controladorPago.validarPago(datosPagoDTO);

        verify(servicioPago).validarTarjeta(datosPagoDTO.getNumeroTarjeta(), datosPagoDTO.getCvv());
        verify(servicioPago, never()).registrarPago(reserva);

        assertThat(mav.getViewName(), equalToIgnoringCase("formulario-pago"));
        assertThat(mav.getModel().get("error"), is("CVV Inválido"));
    }
}
