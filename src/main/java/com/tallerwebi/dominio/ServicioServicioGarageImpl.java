package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("garageService")
@Transactional
public class ServicioServicioGarageImpl implements ServicioGarage {

    private RepositorioGarage repositorioGarage;

    @Autowired
    public ServicioServicioGarageImpl(RepositorioGarage repositorioGarage) { this.repositorioGarage = repositorioGarage; }

    @Override
    public List<Garage> getAll() {
        return repositorioGarage.findAll();
    }

    @Override
    public Garage findById(Integer id) {
        return repositorioGarage.findById(id);
    }
}
