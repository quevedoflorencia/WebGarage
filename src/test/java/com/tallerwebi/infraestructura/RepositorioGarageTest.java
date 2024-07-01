package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioGarage;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import com.tallerwebi.dominio.model.TipoVehiculo;
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

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerGaragesConPaginacion() {
        // Insertar garages de prueba
        for (int i = 1; i <= 10; i++) {
            Garage garage = new Garage();
            garage.setCapacidad(i * 5);
            this.sessionFactory.getCurrentSession().save(garage);
        }

        // Primera página con tamaño 3
        List<Garage> resultPage1 = this.repositorioGarage.obtenerPaginacion(1, 3);

        assertThat(resultPage1, hasSize(3));
        assertThat(resultPage1.get(0).getCapacidad(), equalTo(5));
        assertThat(resultPage1.get(1).getCapacidad(), equalTo(10));
        assertThat(resultPage1.get(2).getCapacidad(), equalTo(15));

        // Segunda página con tamaño 3
        List<Garage> resultPage2 = this.repositorioGarage.obtenerPaginacion(2, 3);

        assertThat(resultPage2, hasSize(3));
        assertThat(resultPage2.get(0).getCapacidad(), equalTo(20));
        assertThat(resultPage2.get(1).getCapacidad(), equalTo(25));
        assertThat(resultPage2.get(2).getCapacidad(), equalTo(30));

        // Tercera página con tamaño 3
        List<Garage> resultPage3 = this.repositorioGarage.obtenerPaginacion(3, 3);

        assertThat(resultPage3, hasSize(3));
        assertThat(resultPage3.get(0).getCapacidad(), equalTo(35));
        assertThat(resultPage3.get(1).getCapacidad(), equalTo(40));
        assertThat(resultPage3.get(2).getCapacidad(), equalTo(45));

        // Cuarta página con tamaño 3, debería tener solo 1 garage
        List<Garage> resultPage4 = this.repositorioGarage.obtenerPaginacion(4, 3);

        assertThat(resultPage4, hasSize(1));
        assertThat(resultPage4.get(0).getCapacidad(), equalTo(50));
    }

    @Test
    @Transactional
    @Rollback
    public void queSeDevuelvaListaVaciaCuandoPaginaExcedeElTotal() {
        // Insertar garages de prueba
        for (int i = 1; i <= 5; i++) {
            Garage garage = new Garage();
            garage.setCapacidad(i * 5);
            this.sessionFactory.getCurrentSession().save(garage);
        }

        // Página que excede el total
        List<Garage> result = this.repositorioGarage.obtenerPaginacion(3, 3);

        assertThat(result, is(empty()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerGaragesPorTipoVehiculo() {
        TipoVehiculo tipoVehiculo = new TipoVehiculo();
        tipoVehiculo.setDescripcion("Auto");

        this.sessionFactory.getCurrentSession().save(tipoVehiculo);

        Garage garage1 = new Garage();
        garage1.setCapacidad(5);
        GarageTipoVehiculo garageTipoVehiculo1 = new GarageTipoVehiculo();
        garageTipoVehiculo1.setGarage(garage1);
        garageTipoVehiculo1.setTipoVehiculo(tipoVehiculo);
        garage1.setGarageTipoVehiculos(List.of(garageTipoVehiculo1));

        Garage garage2 = new Garage();
        garage2.setCapacidad(10);
        GarageTipoVehiculo garageTipoVehiculo2 = new GarageTipoVehiculo();
        garageTipoVehiculo2.setGarage(garage2);
        garageTipoVehiculo2.setTipoVehiculo(tipoVehiculo);
        garage2.setGarageTipoVehiculos(List.of(garageTipoVehiculo2));

        this.sessionFactory.getCurrentSession().save(garage1);
        this.sessionFactory.getCurrentSession().save(garage2);
        this.sessionFactory.getCurrentSession().save(garageTipoVehiculo1);
        this.sessionFactory.getCurrentSession().save(garageTipoVehiculo2);

        List<Garage> result = this.repositorioGarage.getGaragesPorTipoVehiculo(tipoVehiculo.getId());

        assertThat(result, hasSize(2));
        assertThat(result.get(0).getCapacidad(), equalTo(5));
        assertThat(result.get(1).getCapacidad(), equalTo(10));
    }

    @Test
    @Transactional
    @Rollback
    public void queSeDevuelvaListaVaciaCuandoNoHayGaragesDelTipoVehiculo() {
        TipoVehiculo tipoVehiculo = new TipoVehiculo();
        tipoVehiculo.setDescripcion("Moto");

        this.sessionFactory.getCurrentSession().save(tipoVehiculo);

        List<Garage> result = this.repositorioGarage.getGaragesPorTipoVehiculo(tipoVehiculo.getId());

        assertThat(result, is(empty()));
    }


}
