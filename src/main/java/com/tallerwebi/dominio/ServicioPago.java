package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.presentacion.dto.DatosPagoDTO;

public interface ServicioPago {
    Boolean validarNumeroTarjeta (String numeroTarjeta);
    void registrarPago(DatosPagoDTO datosPagoDTO);

    Boolean validarCvv(String cvv);
}
