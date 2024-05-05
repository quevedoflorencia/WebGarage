package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.GarageRepository;
import com.tallerwebi.dominio.model.Garage;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("garageRepository")
public class GarageRepositoryImpl implements GarageRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public GarageRepositoryImpl(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    public List<Garage> findAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Garage.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
