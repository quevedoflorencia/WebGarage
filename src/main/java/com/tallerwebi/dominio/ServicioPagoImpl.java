package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Pago;
import com.tallerwebi.presentacion.dto.DatosPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service ("servicioPago")
@Transactional
public class ServicioPagoImpl implements ServicioPago{

    private RepositorioPago repositorioPago;

    @Autowired
    public ServicioPagoImpl(RepositorioPago repositorioPago){
        this.repositorioPago= repositorioPago;
    }

    @Override
    public Boolean validarNumeroTarjeta(Long numeroTarjeta) {
        Boolean resultado=false;
        String variablePrueba = numeroTarjeta.toString();
        if (variablePrueba.length()==16){
            resultado=true;
        }
        return resultado;
    }

    @Override
    public void registrarPago(DatosPagoDTO datosPagoDTO, Long idReserva) {

        Pago pago = new Pago(null, idReserva);

        repositorioPago.guardarPago(pago);
    }

}
