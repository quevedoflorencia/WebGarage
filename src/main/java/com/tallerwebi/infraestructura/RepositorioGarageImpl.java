package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioGarage;
import com.tallerwebi.dominio.model.Garage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
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
    public List<Garage> obtenerPaginacion(Integer page, Integer size) {
        Session session = sessionFactory.getCurrentSession();

        // Asegurar que page sea al menos 1
        int pageNumber = Math.max(page, 1);

        // Calcular el offset basado en la página y el tamaño de la página
        int offset = (pageNumber - 1) * size;

        // NOTA: si lo hacia con criteria daba error con el distinct por eso busqué otra forma.
        String sqlQuery = "SELECT * FROM Garage " +
                "ORDER BY id " +
                "LIMIT :size OFFSET :offset";

        // Crear la consulta utilizando Query de Hibernate
        Query query = session.createNativeQuery(sqlQuery, Garage.class);
        query.setParameter("size", size);
        query.setParameter("offset", offset);

        List<Garage> garagesPaginados = query.getResultList();

        return garagesPaginados;

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
