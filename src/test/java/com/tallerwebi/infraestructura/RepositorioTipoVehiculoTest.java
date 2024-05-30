package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTipoVehiculo;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import com.tallerwebi.dominio.model.TipoVehiculo;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
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
public class RepositorioTipoVehiculoTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioTipoVehiculo repositorioTipoVehiculo;

    @BeforeEach
    public void init() {
        this.repositorioTipoVehiculo = new RepositorioTipoVehiculoImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void alListarTiposVehiculosDeberiaDevolverUnaListaConTodosLosTiposVehiculosGuardados() {
        TipoVehiculo tipoVehiculo1 = new TipoVehiculo(null, "Auto", "icono1");
        TipoVehiculo tipoVehiculo2 = new TipoVehiculo(null, "Camioneta", "icono2");
        TipoVehiculo tipoVehiculo3 = new TipoVehiculo(null, "Moto", "icono3");

        this.sessionFactory.getCurrentSession().save(tipoVehiculo1);
        this.sessionFactory.getCurrentSession().save(tipoVehiculo2);
        this.sessionFactory.getCurrentSession().save(tipoVehiculo3);

        List<TipoVehiculo> result = this.repositorioTipoVehiculo.listar();

        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(greaterThanOrEqualTo(1)));
    }
}
