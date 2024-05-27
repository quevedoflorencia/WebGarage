package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioGarage;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioGarageTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioGarage repositorioGarage;

    @BeforeEach
    public void init() { this.repositorioGarage = new RepositorioGarageImpl(this.sessionFactory); }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerTodosLosGaragesSinFiltrosYDevuelvaUnaListaConGarages() {
        Garage garage1 = new Garage();
        garage1.setCapacidad(5);
        Garage garage2 = new Garage();
        garage2.setCapacidad(10);
        Garage garage3 = new Garage();
        garage3.setCapacidad(20);

        this.sessionFactory.getCurrentSession().save(garage1);
        this.sessionFactory.getCurrentSession().save(garage2);
        this.sessionFactory.getCurrentSession().save(garage3);

        List<Garage> result = this.repositorioGarage.findAll();

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerTodosLosGaragesSinFiltrosPeroDevuelvaUnaListaVacia() {
        List<Garage> result = this.repositorioGarage.findAll();

        assertThat(result, is(empty()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnGaragePorIdentificador() {
        Garage garage = new Garage();
        garage.setCapacidad(5);

        this.sessionFactory.getCurrentSession().save(garage);

        Garage result = this.repositorioGarage.findById(garage.getId());

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), equalTo(garage.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnGarageSegunUnaCapacidadDadaPorParametro() {
        Garage garage1 = new Garage();
        garage1.setCapacidad(5);
        Garage garage2 = new Garage();
        garage2.setCapacidad(10);
        Garage garage3 = new Garage();
        garage3.setCapacidad(20);

        this.sessionFactory.getCurrentSession().save(garage1);
        this.sessionFactory.getCurrentSession().save(garage2);
        this.sessionFactory.getCurrentSession().save(garage3);

        List<Garage> result = this.repositorioGarage.getGarageSegunCapacidad(10);

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getCapacidad(), equalTo(10));
    }
}
