package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import com.tallerwebi.dominio.RepositorioGarageTipoVehiculo;
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
public class RepositorioGarageTipoVehiculoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioGarageTipoVehiculo repositorioGarageTipoVehiculo;

    @BeforeEach
    public void init() {
        this.repositorioGarageTipoVehiculo = new RepositorioGarageTipoVehiculoImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaListarLosTiposDeVehiculosDeLosGarages() {
        GarageTipoVehiculo tipoVehiculo1 = new GarageTipoVehiculo(1, 10.0, null, null);
        GarageTipoVehiculo tipoVehiculo2 = new GarageTipoVehiculo(2, 12.0, null, null);

        this.sessionFactory.getCurrentSession().save(tipoVehiculo1);
        this.sessionFactory.getCurrentSession().save(tipoVehiculo2);

        List<GarageTipoVehiculo> result = this.repositorioGarageTipoVehiculo.listarGarageTiposVehiculos();

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(greaterThanOrEqualTo(2)));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnTipoDeVehiculoPorId() {
        GarageTipoVehiculo tipoVehiculo = new GarageTipoVehiculo(1, 10.0, null, null);

        this.sessionFactory.getCurrentSession().save(tipoVehiculo);

        GarageTipoVehiculo result = this.repositorioGarageTipoVehiculo.obtenerById(tipoVehiculo.getId());

        assertThat(result, is(notNullValue()));
        assertThat(result.getPrecioHora(), equalTo(10.0));
    }
}
