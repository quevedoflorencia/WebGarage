package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPago;
import com.tallerwebi.dominio.model.Pago;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioPagoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioPago repositorioPago;

    @BeforeEach
    public void init() {
        this.repositorioPago = new RepositorioPagoImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnPago() {
        Reserva reserva = new Reserva();
        this.sessionFactory.getCurrentSession().save(reserva);

        Pago pago = new Pago(reserva);

        this.repositorioPago.guardarPago(pago);

        assertThat(pago.getId(), is(notNullValue()));
        assertThat(pago.getReserva().getId(), equalTo(reserva.getId()));
    }
}
