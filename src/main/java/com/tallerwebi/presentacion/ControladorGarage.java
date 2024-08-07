package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCalificacion;
import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.ServicioTipoVehiculo;
import com.tallerwebi.dominio.model.Garage;

import com.tallerwebi.dominio.model.TipoVehiculo;
import com.tallerwebi.presentacion.dto.CalificacionDTO;
import com.tallerwebi.presentacion.dto.CalificacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/garages")
public class ControladorGarage {

    private ServicioGarage servicioGarage;
    private final ServicioTipoVehiculo servicioTipoVehiculo;
    private ServicioCalificacion servicioCalificacion;

    @Autowired
    public ControladorGarage(ServicioGarage servicioGarage, ServicioTipoVehiculo servicioTipoVehiculo, ServicioCalificacion servicioCalificacion) {
        this.servicioGarage = servicioGarage;
        this.servicioTipoVehiculo = servicioTipoVehiculo;
        this.servicioCalificacion = servicioCalificacion;
    }

    @RequestMapping(path = "/listado", method = RequestMethod.GET)
    public ModelAndView inicio(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "12") Integer size,
                               @RequestParam(required = false) Integer tipoVehiculo,
                               @RequestParam(required = false) String ordenarPorCalificacion,
                               @RequestParam(required = false) String filter
    ) {

        ModelMap model = new ModelMap();
        page = servicioGarage.validarPagina(page);
        size = servicioGarage.validarTamanioPagina(size);

        List<Garage> garagesPaginados;
        garagesPaginados = servicioGarage.getPaginacion(page, size, tipoVehiculo, ordenarPorCalificacion, filter);

        Integer totalGarages = servicioGarage.obtenerCantidadTotal(tipoVehiculo, filter);

        Integer totalPages = servicioGarage.calcularTotalPaginas(totalGarages, size);
        List<Integer> pageNumbers = servicioGarage.generarNumerosPagina(totalPages);

        List<TipoVehiculo> tiposVehiculos = servicioTipoVehiculo.traerTodos();

        model.put("garages", garagesPaginados);
        model.put("pageNumbers", pageNumbers);
        model.put("currentPage", page);
        model.put("pageSize", size);
        model.put("filter", filter);
        model.put("tipoVehiculos", tiposVehiculos);
        model.put("tipoVehiculo", tipoVehiculo);
        model.put("ordenarPorCalificacion", ordenarPorCalificacion);


        return new ModelAndView("listar-garages", model);
    }

    @RequestMapping("/calificar/{id}")
    public ModelAndView irCalificar(@PathVariable("id") Integer garageId) {

        ModelMap modelo = new ModelMap();

        CalificacionDTO calificacionData = new CalificacionDTO();
        calificacionData.setIdGarage(garageId);
        modelo.put("calificacionData", calificacionData);

        return new ModelAndView("formulario-calificar", modelo);
    }


    @RequestMapping(path = "/validar", method = RequestMethod.POST)
    public ModelAndView validarCalificacion(@ModelAttribute("calificacionData") CalificacionDTO calificacionDTO) {

        ModelMap model = new ModelMap();

        Garage garage = servicioGarage.buscarPorId(calificacionDTO.getIdGarage());
        servicioCalificacion.guardarCalificacion(calificacionDTO.getPuntaje(), calificacionDTO.getComentario(), garage);

        Double promedioActualizado = servicioGarage.actualizarPromedio(calificacionDTO.getIdGarage());
        garage.setPromedio(promedioActualizado);
        servicioGarage.guardarPromedio(garage);

        model.put("promedioActualizado", promedioActualizado);

        return new ModelAndView("redirect:/home");
    }
}