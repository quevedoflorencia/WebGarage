package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.dominio.ReservationService;
import com.tallerwebi.dominio.ReservationServiceImpl;
import com.tallerwebi.dominio.excepcion.ExistUserException;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReservationController {

    private GarageService garageService;
    private ReservationService reservationService;


    @Autowired
    public ReservationController(GarageService garageService, ReservationService reservationService) {
        this.garageService = garageService;
        this.reservationService = reservationService;
    }


    @RequestMapping("/pre-reservation")
    public ModelAndView goToPreReservation(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        List<String> totalHoursList = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00");

        List<Garage> garages = garageService.getAll();
        Garage garage = garages.get(0);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setGarageId(garage.getId());
        reservationDTO.setUserId((Long) request.getSession().getAttribute("ID"));

        model.put("totalHours", totalHoursList);
        model.put("reservation", reservationDTO);

        // chequea si hay un mensaje de error en el modelo y pasarlo a la vista
        String error = (String) request.getSession().getAttribute("error");
        model.put("error", error);

        // Elimina el mensaje de error de la sesión para que no se muestre en la próxima solicitud
        request.getSession().removeAttribute("error");

        return new ModelAndView("pre-reservation", model);
    }


    @RequestMapping(path = "/reservation/confirm", method = RequestMethod.POST)
    public ModelAndView confirmReservation(@ModelAttribute("reservation") ReservationDTO reservationDTO, HttpServletRequest request) {

        ModelMap model = new ModelMap();
        try{

            Garage garage = garageService.findById(reservationDTO.garageId);
            model.put("garage", garage);
            model.put("reservation", reservationDTO);

            reservationService.addReservation(reservationDTO);

        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error to confirm reservation" + e);
            return new ModelAndView("redirect:/pre-reservation");
        }
        //TODO: redirigir a mis reservas-
        return new ModelAndView("confirm-reservation", model);
    }
}
