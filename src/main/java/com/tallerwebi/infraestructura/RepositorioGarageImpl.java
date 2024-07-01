package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioGarage;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
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

    @Override
    public List<Garage> getGaragesPorTipoVehiculo(Integer tipoVehiculoId){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Garage> criteriaQuery = criteriaBuilder.createQuery(Garage.class);
        Root<Garage> garageRoot = criteriaQuery.from(Garage.class);
        Join<Garage, GarageTipoVehiculo> joinGarageTipoVehiculo = garageRoot.join("garageTipoVehiculos");

        criteriaQuery.select(garageRoot)
                .where(criteriaBuilder.equal(joinGarageTipoVehiculo.get("tipoVehiculo").get("id"), tipoVehiculoId));

        return session.createQuery(criteriaQuery).getResultList();
    }
}
