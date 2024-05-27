package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioGarage;
import com.tallerwebi.dominio.model.Garage;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("garageRepository")
public class RepositorioGarageImpl implements RepositorioGarage {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioGarageImpl(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    public List<Garage> findAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Garage.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public Garage findById(Integer id) {
        return sessionFactory.getCurrentSession().get(Garage.class, id);
    }

    @Override
    public List getGarageSegunCapacidad(Integer capacidadBuscada) {

        return sessionFactory.getCurrentSession().createCriteria(Garage.class).add(Restrictions.eq("capacidad",capacidadBuscada)).list();

    }
}
