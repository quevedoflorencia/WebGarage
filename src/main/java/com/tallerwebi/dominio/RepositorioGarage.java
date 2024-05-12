package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface RepositorioGarage {

    List<Garage> findAll();
    Garage findById(Integer id);
}
