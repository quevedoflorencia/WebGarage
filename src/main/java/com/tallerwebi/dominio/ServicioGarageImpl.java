package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioGarage")
@Transactional
public class ServicioGarageImpl implements ServicioGarage {

    private RepositorioGarage repositorioGarage;

    @Autowired
    public ServicioGarageImpl(RepositorioGarage repositorioGarage) { this.repositorioGarage = repositorioGarage; }

    @Override
    public List<Garage> traerTodos() {
        return repositorioGarage.findAll();
    }

    @Override
    public Garage buscarPorId(Integer id) {
        return repositorioGarage.findById(id);
    }

    @Override
    public List<Garage> getGaragesPorCapacidad(Integer capacidadBuscada) {
        return repositorioGarage.getGarageSegunCapacidad(capacidadBuscada);
    }
}
