package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dto.DatosPagoDTO;

public interface ServicioPago {
    Boolean validarNumeroTarjeta (Long numeroTarjeta);
    void registrarPago(DatosPagoDTO datosPagoDTO, Long idReserva);

}
