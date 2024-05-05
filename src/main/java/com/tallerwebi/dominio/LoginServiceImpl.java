package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExistUserException;
import com.tallerwebi.dominio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User consultarUsuario (String email, String password) {
        return userRepository.buscarUsuario(email, password);
    }

    @Override
    public void registrar(User user) throws ExistUserException {
        User userEncontrado = userRepository.buscarUsuario(user.getEmail(), user.getPassword());
        if(userEncontrado != null){
            throw new ExistUserException();
        }
        userRepository.guardar(user);
    }

}

