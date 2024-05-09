package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.excepcion.ExistUserException;
import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.dominio.ReservationRepository;
import com.tallerwebi.dominio.model.User;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reservationRespository")
public class ReservationRepositoryImpl implements ReservationRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public ReservationRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List reservationByClient(String client) {
        return null;
    }

    @Override
    public List reservationByDate(String date) {
        final Session session = sessionFactory.getCurrentSession();
        return sessionFactory.getCurrentSession().createCriteria(Reservation.class).add(Restrictions.eq("day",date)).list();
    }

    @Override
    public void addNewReservation(ReservationDTO reservation) {
        Reservation newReservation = new Reservation();
        newReservation.setUserId(reservation.getUserId());
        newReservation.setDay(reservation.getDate());
        newReservation.setStartTime(reservation.getStartTime());
        newReservation.setFinishTime(reservation.getFinishTime());

        // Guardar la reserva en la base de datos
        sessionFactory.getCurrentSession().save(newReservation);
    }


    @Override
    public List allReservations() {
        return null;
    }

    @Override
    public  Reservation reservationByIdUser(Long id) {
        return sessionFactory.getCurrentSession().get(Reservation.class, id);
    }

    @Override
    public List obtenerReservasByUserId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return sessionFactory.getCurrentSession().createCriteria(Reservation.class).add(Restrictions.eq("userId",id)).list();

    }
}
