package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Garage;

import java.util.List;

public interface ServicioGarage {

    List<Garage> traerTodos();
    Garage buscarPorId(Integer id);


}
