package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.TipoVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioTipoVehiculo")
@Transactional
public class ServicioTipoVehiculoImpl implements ServicioTipoVehiculo {

    private RepositorioTipoVehiculo repositorioTipoVehiculo;

    @Autowired
    public ServicioTipoVehiculoImpl(RepositorioTipoVehiculo repositorioTipoVehiculo){
        this.repositorioTipoVehiculo = repositorioTipoVehiculo;
    }

    @Override
    public List<TipoVehiculo> traerTodos () {
        return repositorioTipoVehiculo.listarTiposVehiculos();
    }

}

