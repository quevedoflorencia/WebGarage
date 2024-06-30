package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCalificacion;
import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.model.Garage;

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
    private ServicioCalificacion servicioCalificacion;


    @Autowired
    public ControladorGarage(ServicioGarage servicioGarage, ServicioCalificacion servicioCalificacion) {
        this.servicioGarage = servicioGarage;
        this.servicioCalificacion = servicioCalificacion;
    }

    @RequestMapping(path = "/listado/", method = RequestMethod.GET)
    public ModelAndView inicio(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "3") Integer size) {
        ModelMap model = new ModelMap();
        page = servicioGarage.validarPagina(page);
        size = servicioGarage.validarTamanioPagina(size);

        List<Garage> garagesPaginados = servicioGarage.getPaginacion(page, size);
        Integer totalGarages = servicioGarage.traerTodos().size();
        Integer totalPages = servicioGarage.calcularTotalPaginas(totalGarages, size);
        List<Integer> pageNumbers = servicioGarage.generarNumerosPagina(totalPages);

        model.put("garages", garagesPaginados);
        model.put("pageNumbers", pageNumbers);
        model.put("currentPage", page);
        model.put("pageSize", size);

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

       /* ModelMap model = new ModelMap();*/

        /*Garage garage = servicioGarage.buscarPorId(calificacionDTO.getIdGarage());/
        /*servicioCalificacion.validarPuntaje(calificacionDTO.getPuntaje());*/

        Integer puntaje = calificacionDTO.getPuntaje();
        String comentario = calificacionDTO.getComentarioCalificacion();
        Integer idGarage = calificacionDTO.getIdGarage();

        servicioCalificacion.guardarCalificacion(puntaje, comentario, idGarage);

        return new ModelAndView("home");
    }
}