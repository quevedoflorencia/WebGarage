package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioGarage;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioGarageTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioGarage repositorioGarage;

    @BeforeEach
    public void init() { this.repositorioGarage = new RepositorioGarageImpl(this.sessionFactory); }
}
