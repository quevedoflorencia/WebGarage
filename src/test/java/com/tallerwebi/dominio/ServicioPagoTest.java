package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionCvvTarjetaInvalida;
import com.tallerwebi.dominio.excepcion.ExcepcionNumeroTarjetaInvalida;
import com.tallerwebi.dominio.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioPagoTest {

    private RepositorioPago repositorioPago;
    private ServicioReserva servicioReserva;
    private ServicioPago servicioPago;

    @BeforeEach
    public void init(){
        this.repositorioPago = mock(RepositorioPago.class);
        this.servicioReserva = mock(ServicioReserva.class);
        this.servicioPago = new ServicioPagoImpl(repositorioPago);
    }

    @Test
    public void cuandoSeValidaUnaTarjetaConNumeroCorrectoYCVVCorrectoNODebeLanzarExcepcion() throws ExcepcionNumeroTarjetaInvalida, ExcepcionCvvTarjetaInvalida {
        String numeroTarjeta = "1234567812345678";
        String cvv = "123";

        servicioPago.validarTarjeta(numeroTarjeta, cvv);
    }


    @Test
    public void cuandoSeValidaUnaTarjetaConNumeroIncorrectoDebeLanzarExcepcionNumeroTarjetaInvalida() {
        String numeroTarjetaIncorrecto = "12345678";
        String cvv = "123";

        assertThrows(ExcepcionNumeroTarjetaInvalida.class, () -> servicioPago.validarTarjeta(numeroTarjetaIncorrecto, cvv));
    }

    @Test
    public void cuandoSeValidaUnaTarjetaConCVVIncorrectoDebeLanzarExcepcionCvvTarjetaInvalida() {
        String numeroTarjeta = "1234567812345678";
        String cvvIncorrecto = "12";

        assertThrows(ExcepcionCvvTarjetaInvalida.class, () -> servicioPago.validarTarjeta(numeroTarjeta, cvvIncorrecto));
    }

    @Test
    public void cuandoSeRegistraUnPagoDeReservaDebeGuardarseCorrectamente() {
        Usuario usuario = new Usuario();
        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo();
        Garage garage = new Garage();
        EstadoReserva estadoReserva = new EstadoReserva();

        Reserva reserva = new Reserva(usuario, garage, garageTipoVehiculo, "2024-05-26", "10:00", "12:00", 1000.00, estadoReserva);
        Pago pagoMock = new Pago(reserva);

        doNothing().when(repositorioPago).guardarPago(pagoMock);

        servicioPago.registrarPago(reserva);

        verify(repositorioPago, times(1)).guardarPago(any(Pago.class));
    }
}
