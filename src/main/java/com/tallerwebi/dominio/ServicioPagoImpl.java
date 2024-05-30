package com.tallerwebi.dominio;

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

        this.repositorioPago= repositorioPago;
        this.servicioReserva=servicioReserva;
    }

    @Override
    public Boolean validarNumeroTarjeta(String numeroTarjeta) {

        if (numeroTarjeta.length()==16){
            return true;
        }
        return false;
    }
    @Override
    public Boolean validarCvv(String cvv) {

        if (cvv.length()==3){
            return true;
        }
        return false;

    }
    @Override
    public void registrarPago(DatosPagoDTO datosPagoDTO) {

        Long idReserva= datosPagoDTO.getIdReserva();
        Reserva reserva = servicioReserva.buscarPorId(idReserva);

        Pago pago = new Pago(reserva);

        repositorioPago.guardarPago(pago);
    }



}
