package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioGarageTipoVehiculo;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioGarageTipoVehiculo")
public class RepositorioGarageTipoVehiculoImpl implements RepositorioGarageTipoVehiculo {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioGarageTipoVehiculoImpl(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    public List<GarageTipoVehiculo> listarGarageTiposVehiculos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(GarageTipoVehiculo.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public GarageTipoVehiculo obtenerById(Integer id) {
        return sessionFactory.getCurrentSession().get(GarageTipoVehiculo.class, id);
    }
}
