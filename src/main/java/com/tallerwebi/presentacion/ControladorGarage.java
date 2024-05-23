package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioGarage;
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
@RequestMapping("/garages")
public class ControladorGarage {
    private ServicioGarage servicioGarage;
    private RepositorioGarage repositorioGarage;

    @Autowired
    public ControladorGarage(ServicioGarage servicioGarage, RepositorioGarage repositorioGarage) {
        this.servicioGarage = servicioGarage;
        this.repositorioGarage = repositorioGarage;
    }

    @RequestMapping(path = "/listar", method = RequestMethod.GET)
    public ModelAndView listarGarages() {

        ModelMap model = new ModelMap();
        /*
        HttpSession session = request.getSession();

        Long userId = (Long) session.getAttribute("ID");
        if(userId == null) {
            return new ModelAndView("redirect:/login");
        }
        */
        List<Garage> garages = servicioGarage.traerTodos();

        model.put("garages", garages);

        return new ModelAndView("listar-garages", model);
    }



}
