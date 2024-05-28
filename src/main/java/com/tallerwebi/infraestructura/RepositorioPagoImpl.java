package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPago;
import org.springframework.stereotype.Repository;

@Repository("repositorioPago")
public class RepositorioPagoImpl implements RepositorioPago {


    @Override
    public Boolean validarNumeroTarjeta(String numero) {

        if (numero.length()==16){
            return true;
        }

        return false;
    }
}
