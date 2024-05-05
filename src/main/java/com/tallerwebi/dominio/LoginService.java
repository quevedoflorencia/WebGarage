package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExistUserException;
import com.tallerwebi.dominio.model.User;

public interface LoginService {

    User consultarUsuario(String email, String password);
    void registrar(User user) throws ExistUserException;

}
