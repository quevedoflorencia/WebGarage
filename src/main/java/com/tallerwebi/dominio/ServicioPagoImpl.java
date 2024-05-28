package com.tallerwebi.dominio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioPago")
@Transactional
public class ServicioPagoImpl implements ServicioPago{
    private RepositorioPago repositorioPago;


    @Autowired
    public ServicioPagoImpl(RepositorioPago repositorioPago) {
        this.repositorioPago = repositorioPago;
    }

    @Override
    public Boolean validarNumeroTarjeta(String numero) {
        return repositorioPago.validarNumeroTarjeta(numero);
    }
}
