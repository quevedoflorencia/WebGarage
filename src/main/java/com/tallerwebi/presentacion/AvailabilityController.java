package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.List;

@Controller
public class AvailabilityController {

    @RequestMapping("/pre-reservation")
    public ModelAndView goToPreReservation() {
        List<String> totalHoursList = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00");

        ModelMap model = new ModelMap();
        model.put("totalHours", totalHoursList);

        return new ModelAndView("pre-reservation", model);
    }

}
