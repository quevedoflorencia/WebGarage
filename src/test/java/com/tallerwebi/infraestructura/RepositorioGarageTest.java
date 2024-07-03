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
import java.time.LocalTime;
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
    public void queSePuedaObtenerTodosLosGarages() {
        Garage garage1 = dadoUnGarage();
        Garage garage2 = dadoUnGarage();

        List<Garage> result = this.repositorioGarage.findAll();

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(2));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerGaragesPaginados() {
        for (int i = 0; i < 5; i++) {
            dadoUnGarage();
        }

        List<Garage> result = this.repositorioGarage.obtenerPaginacion(1, 3);

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(3));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnGaragePorSuID() {
        Garage garage = dadoUnGarage();

        Garage result = this.repositorioGarage.findById(garage.getId());

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), equalTo(garage.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerGaragesPorCapacidad() {
        Garage garage = dadoUnGarage();
        GarageTipoVehiculo garageTipoVehiculo = dadoUnGarageTipoVehiculo();
        garage.getGarageTipoVehiculos().add(garageTipoVehiculo);
        garageTipoVehiculo.setCapacidad(10);
        this.sessionFactory.getCurrentSession().save(garage);

        List<Garage> result = this.repositorioGarage.getGarageSegunCapacidad(10);

        assertThat(result, is(not(empty())));
        assertThat(result.get(0).getGarageTipoVehiculos().get(0).getCapacidad(), equalTo(10));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedanObtenerGaragesPorTipoVehiculo() {
        GarageTipoVehiculo tipoVehiculo = dadoUnGarageTipoVehiculo();
        Garage garage = dadoUnGarage();
        garage.getGarageTipoVehiculos().add(tipoVehiculo);
        this.sessionFactory.getCurrentSession().save(garage);

        List<Garage> result = this.repositorioGarage.getGaragesPorTipoVehiculo(tipoVehiculo.getId());

        assertThat(result, is(not(empty())));
        assertThat(result.get(0).getId(), equalTo(garage.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarPromedioEnGarage() {
        Garage garage = dadoUnGarage();
        double nuevoPromedio = 4.5;
        garage.setPromedio(nuevoPromedio);

        this.repositorioGarage.guardarPromedio(garage);

        Garage updatedGarage = this.repositorioGarage.findById(garage.getId());
        assertThat(updatedGarage.getPromedio(), equalTo(nuevoPromedio));
    }

    private Garage dadoUnGarage() {
        Garage garage = new Garage();
        garage.setNombre("Garage Test");
        garage.setHorarioApertura(LocalTime.of(8, 0));
        garage.setHorarioCierre(LocalTime.of(20, 0));
        garage.setLatitud("10.0000");
        garage.setLongitud("20.0000");
        garage.setRutaFoto("ruta/foto.jpg");
        garage.setPromedio(0.0);

        this.sessionFactory.getCurrentSession().save(garage);

        return garage;
    }

    private GarageTipoVehiculo dadoUnGarageTipoVehiculo() {
        GarageTipoVehiculo garageTipoVehiculo = new GarageTipoVehiculo();
        garageTipoVehiculo.setTipoVehiculo(new TipoVehiculo());
        garageTipoVehiculo.setCapacidad(10);

        this.sessionFactory.getCurrentSession().save(garageTipoVehiculo);

        return garageTipoVehiculo;
    }
}
