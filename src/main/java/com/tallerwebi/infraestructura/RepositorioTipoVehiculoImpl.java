package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTipoVehiculo;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.TipoVehiculo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioTipoVehiculo")
public class RepositorioTipoVehiculoImpl implements RepositorioTipoVehiculo {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioTipoVehiculoImpl(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    public List<TipoVehiculo> listarTiposVehiculos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(TipoVehiculo.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
