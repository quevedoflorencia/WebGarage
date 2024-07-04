package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioNotificacion;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.dominio.model.ReservaNotificacion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("repositorioNotificacion")
public class RepositorioNotificacionImpl implements RepositorioNotificacion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioNotificacionImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(ReservaNotificacion reservaNotificacion) {
        sessionFactory.getCurrentSession().save(reservaNotificacion);
    }

    @Override
    public ReservaNotificacion buscarPorIdReserva(Long reservaId) {
        String sqlQuery = "SELECT * FROM ReservaNotificacion WHERE id_reserva = :reservaID" ;

        ReservaNotificacion reservaNotificacion = sessionFactory.getCurrentSession().createNativeQuery(sqlQuery, ReservaNotificacion.class).setParameter("reservaID", reservaId ).uniqueResult();

        if(reservaNotificacion !=null){
            return reservaNotificacion;
        }
        return null;
    }
}
