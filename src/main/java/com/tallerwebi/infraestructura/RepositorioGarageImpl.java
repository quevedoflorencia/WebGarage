package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioGarage;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
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
    public List<Garage> obtenerPaginacion(Integer page, Integer size, Integer idTipoVehiculo, String orderByCalificacion, String busqueda) {
        Session session = sessionFactory.getCurrentSession();

        // Asegurar que page sea al menos 1
        int pageNumber = Math.max(page, 1);

        // Calcular el offset basado en la página y el tamaño de la página
        int offset = (pageNumber - 1) * size;

        StringBuilder sqlQuery = new StringBuilder("SELECT DISTINCT g.* FROM Garage g ");

        sqlQuery.append("LEFT JOIN garage_tipo_vehiculo gtv ON g.id = gtv.id_garage ");

        if (idTipoVehiculo != null) {
            sqlQuery.append("WHERE gtv.id_tipo_vehiculo = :idTipoVehiculo ");
        }

        if (busqueda != null && !busqueda.isEmpty()) {
            if (idTipoVehiculo != null) {
                sqlQuery.append("AND ");
            } else {
                sqlQuery.append("WHERE ");
            }
            sqlQuery.append("(g.nombre LIKE :busqueda OR g.calle LIKE :busqueda) ");
        }

        // Agregar ordenación según el parámetro orderByCalificacion
        if (orderByCalificacion != null && !orderByCalificacion.isEmpty() && (orderByCalificacion.equalsIgnoreCase("asc") || orderByCalificacion.equalsIgnoreCase("desc"))) {
            sqlQuery.append("ORDER BY promedio ").append(orderByCalificacion).append(" "); // ordenamiento en
        } else {
            sqlQuery.append("ORDER BY id "); // Ordenar por ID
        }

        sqlQuery.append("LIMIT :size OFFSET :offset");

        // Crea la consulta utilizando Query de Hibernate
        Query query = session.createNativeQuery(sqlQuery.toString(), Garage.class);
        query.setParameter("size", size);
        query.setParameter("offset", offset);

        if (idTipoVehiculo != null) {
            query.setParameter("idTipoVehiculo", idTipoVehiculo);
        }
        if (busqueda != null && !busqueda.isEmpty()) {
            query.setParameter("busqueda", "%" + busqueda + "%");
        }

        List<Garage> garagesPaginados = query.getResultList();

        return garagesPaginados;
    }

    @Override
    public Integer getCount(Integer idTipoVehiculo, String busqueda) {
        Session session = sessionFactory.getCurrentSession();

        StringBuilder sqlQuery = new StringBuilder("SELECT DISTINCT COUNT(g.id) FROM Garage g ");

        boolean whereClauseAdded = false;

        if (idTipoVehiculo != null) {
            sqlQuery.append("LEFT JOIN garage_tipo_vehiculo gtv ON g.id = gtv.id_garage ");
            sqlQuery.append("WHERE gtv.id_tipo_vehiculo = :idTipoVehiculo ");
            whereClauseAdded = true;
        }

        if (busqueda != null && !busqueda.isEmpty()) {
            if (whereClauseAdded) {
                sqlQuery.append("AND ");
            } else {
                sqlQuery.append("WHERE ");
                whereClauseAdded = true;
            }
            sqlQuery.append("(g.nombre LIKE :busqueda OR g.calle LIKE :busqueda) ");
        }

        Query query = session.createNativeQuery(sqlQuery.toString());

        if (idTipoVehiculo != null) {
            query.setParameter("idTipoVehiculo", idTipoVehiculo);
        }
        if (busqueda != null && !busqueda.isEmpty()) {
            query.setParameter("busqueda", "%" + busqueda + "%");
        }

        BigInteger count = (BigInteger) query.getSingleResult();

        return count.intValue();
    }


    @Override
    public void guardarPromedio(Garage garage) {
        sessionFactory.getCurrentSession().update(garage);
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
