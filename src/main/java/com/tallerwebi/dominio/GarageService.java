package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface GarageService {

    List<Garage> getAll();
    Garage findById(Integer id);

}
