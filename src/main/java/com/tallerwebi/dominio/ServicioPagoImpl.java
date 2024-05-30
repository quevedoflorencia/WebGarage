package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionCvvTarjetaInvalida;
import com.tallerwebi.dominio.excepcion.ExcepcionNumeroTarjetaInvalida;
import com.tallerwebi.dominio.excepcion.ExcepcionReservaNoExiste;
import com.tallerwebi.dominio.model.Pago;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.presentacion.dto.DatosPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service ("servicioPago")
@Transactional
public class ServicioPagoImpl implements ServicioPago{

    private RepositorioPago repositorioPago;
    private ServicioReserva servicioReserva;

    @Autowired
    public ServicioPagoImpl(RepositorioPago repositorioPago, ServicioReserva servicioReserva){
        this.repositorioPago = repositorioPago;
        this.servicioReserva = servicioReserva;
    }

    @Override
    public void validarTarjeta(String numero, String cvv) throws ExcepcionNumeroTarjetaInvalida, ExcepcionCvvTarjetaInvalida {

        if(numero.isEmpty() || numero.replaceAll("\\s", "").length() != 16) {
            throw new ExcepcionNumeroTarjetaInvalida();
        }

        if(cvv.length() != 3) {
            throw new ExcepcionCvvTarjetaInvalida();
        }
    }

    @Override
    public void registrarPago(Reserva reserva) {
        Pago pago = new Pago(reserva);
        repositorioPago.guardarPago(pago);
    }
}
