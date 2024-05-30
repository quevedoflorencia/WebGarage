package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionCvvTarjetaInvalida;
import com.tallerwebi.dominio.excepcion.ExcepcionNumeroTarjetaInvalida;
import com.tallerwebi.dominio.model.Reserva;

public interface ServicioPago {
    void registrarPago(Reserva reserva);
    void validarTarjeta(String numero, String cvv) throws ExcepcionNumeroTarjetaInvalida, ExcepcionCvvTarjetaInvalida;
}
