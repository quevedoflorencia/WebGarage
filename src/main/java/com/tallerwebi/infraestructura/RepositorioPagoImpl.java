package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPago;
import com.tallerwebi.dominio.model.Pago;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPago")
public class RepositorioPagoImpl implements RepositorioPago {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPagoImpl(SessionFactory sessionFactory){ this.sessionFactory=sessionFactory; }

    @Override
    public void guardarPago(Pago pago) {
        sessionFactory.getCurrentSession().save(pago);
    }
}
