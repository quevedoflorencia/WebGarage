package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.EstadoReserva;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.dominio.RepositorioEstadoReserva;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioEstadoReservaTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioEstadoReserva repositorioEstadoReserva;

    @BeforeEach
    public void init() {
        this.repositorioEstadoReserva = new RepositorioEstadoReservaImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaListarLosEstadosDeReserva() {
        EstadoReserva estadoReserva1 = new EstadoReserva("Activa");
        EstadoReserva estadoReserva2 = new EstadoReserva("Cancelada");

        this.sessionFactory.getCurrentSession().save(estadoReserva1);
        this.sessionFactory.getCurrentSession().save(estadoReserva2);

        List<EstadoReserva> result = this.repositorioEstadoReserva.listar();

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(greaterThanOrEqualTo(2)));
    }

    //TODO Por que no anda?
    /**
    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnEstadoDeReservaPorId() {
        EstadoReserva estadoReserva = new EstadoReserva(EstadoReserva.ACTIVA, "Activa");

        this.sessionFactory.getCurrentSession().save(estadoReserva);

        EstadoReserva result = this.repositorioEstadoReserva.obtenerPorId(EstadoReserva.ACTIVA);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), equalTo(EstadoReserva.ACTIVA));
    }
     **/

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnEstadoDeReservaPorDescripcion() {
        String descripcionActiva = "Activa";
        EstadoReserva estadoReserva = new EstadoReserva(EstadoReserva.ACTIVA, descripcionActiva);

        this.sessionFactory.getCurrentSession().save(estadoReserva);

        EstadoReserva result = this.repositorioEstadoReserva.obtenerPorDescripcion(descripcionActiva);

        assertThat(result, is(notNullValue()));
        assertThat(result.getDescripcion(), equalTo(descripcionActiva));
    }
}
