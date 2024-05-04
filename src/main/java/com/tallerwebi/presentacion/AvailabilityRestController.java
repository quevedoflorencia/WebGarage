package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.AvailabilityService;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AvailabilityRestController {

    private AvailabilityService availabilityService;

    @Autowired
    public AvailabilityRestController(AvailabilityService availabilityService){
        this.availabilityService = availabilityService;
    }

    @RequestMapping(path = "/getAvailableHours", method = RequestMethod.POST)
    public List<String> getAvailableHours(@ModelAttribute("dateDTO") DateDTO date, HttpServletRequest request) {
        List<String> hours = null;
        hours = availabilityService.getAvailableHours(date.getDate());
        return hours;
    }
}

