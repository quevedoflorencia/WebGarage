package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.ServicioTipoVehiculo;
import com.tallerwebi.dominio.ServicioTipoVehiculoImpl;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.TipoVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/garages")
public class ControladorGarage {

    private final ServicioTipoVehiculo servicioTipoVehiculo;
    private ServicioGarage servicioGarage;

    @Autowired
    public ControladorGarage(ServicioGarage servicioGarage, ServicioTipoVehiculo servicioTipoVehiculo) {
        this.servicioGarage = servicioGarage;
        this.servicioTipoVehiculo = servicioTipoVehiculo;
    }

    @RequestMapping(path = "/listado/", method = RequestMethod.GET)
    public ModelAndView inicio(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "3") Integer size,
                               @RequestParam(required = false) String  tipoVehiculo) {

        ModelMap model = new ModelMap();
        page = servicioGarage.validarPagina(page);
        size = servicioGarage.validarTamanioPagina(size);
        Integer tipoVehiculoId = null;

        List<Garage> garagesPaginados;

        Integer totalGarages;

        if (tipoVehiculo != null && !tipoVehiculo.isEmpty() && !"null".equals(tipoVehiculo)) {
            tipoVehiculoId = Integer.parseInt(tipoVehiculo);

            garagesPaginados = servicioGarage.getPaginacion(page, size);
            totalGarages = servicioGarage.obtenerGaragesPorTipoVehiculo(tipoVehiculoId).size();
        }
        else {
            garagesPaginados = servicioGarage.getPaginacion(page, size);
            totalGarages = servicioGarage.traerTodos().size();

        }
        Integer totalPages = servicioGarage.calcularTotalPaginas(totalGarages, size);
        List<Integer> pageNumbers = servicioGarage.generarNumerosPagina(totalPages);

        //Listado de tipo de vehiculos
        List<TipoVehiculo> tiposVehiculos = servicioTipoVehiculo.traerTodos();

        model.put("garages", garagesPaginados);
        model.put("pageNumbers", pageNumbers);
        model.put("currentPage", page);
        model.put("pageSize", size);
        model.put("tipoVehiculos", tiposVehiculos);
        model.put("tipoVehiculoSeleccionado", tipoVehiculoId);

        return new ModelAndView("listar-garages", model);
    }
}
