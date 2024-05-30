package com.tallerwebi.dominio;
import com.tallerwebi.dominio.model.GarageTipoVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service("servicioGarageTipoVehiculo")
@Transactional
public class ServicioGarageTipoVehiculoImpl implements ServicioGarageTipoVehiculo {

    private RepositorioGarageTipoVehiculo repositorioGarageTipoVehiculo;

    @Autowired
    public ServicioGarageTipoVehiculoImpl(RepositorioGarageTipoVehiculo repositorioGarageTipoVehiculo){
        this.repositorioGarageTipoVehiculo = repositorioGarageTipoVehiculo;
    }

    @Override
    public List<GarageTipoVehiculo> traerTodos() {
        return repositorioGarageTipoVehiculo.listarGarageTiposVehiculos();
    }

    @Override
    public GarageTipoVehiculo traerPorId (Integer id) {
        return repositorioGarageTipoVehiculo.obtenerById(id);
    }


}

