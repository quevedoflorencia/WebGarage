package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ReservedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservedRestController {

    private ReservedService availabilityService;

    @Autowired
    public ReservedRestController(ReservedService availabilityService){
        this.availabilityService = availabilityService;
    }

    @RequestMapping(path = "/getAvailableHours", method = RequestMethod.POST)
    public List<String> getAvailableHours(@ModelAttribute("dateDTO") DateDTO date) {
        List<String> hours = null;
        hours = availabilityService.getReservedHours(date.getDate());
        return hours;
    }
}

