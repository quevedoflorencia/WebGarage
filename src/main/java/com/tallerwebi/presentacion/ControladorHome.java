package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioGarage;
import com.tallerwebi.dominio.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorHome {

    private ServicioGarage servicioGarage;

    @Autowired
    public ControladorHome(ServicioGarage servicioGarage) {
        this.servicioGarage = servicioGarage;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {

        ModelMap model = new ModelMap();

        List<Garage> garages = servicioGarage.traerTodos();

        model.put("garages", garages);

        return new ModelAndView("home", model);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        return new ModelAndView("redirect:/reservas/listar");
    }
}
