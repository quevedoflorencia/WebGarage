package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.UserRepository;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User obtenerUsuario(Long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (User) session.createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User buscar(String email) {
        return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

}
