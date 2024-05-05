package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.User;

public interface UserRepository {

    User buscarUsuario(String email, String password);
    void guardar(User user);
    User buscar(String email);
    void modificar(User user);
}

