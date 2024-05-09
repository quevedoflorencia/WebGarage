package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.dominio.ReservationService;
import com.tallerwebi.dominio.UserService;
import com.tallerwebi.dominio.excepcion.GarageNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.dominio.model.User;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private UserService userService;
    private GarageService garageService;
    private ReservationService reservationService;

    @Autowired
    public ReservationController(UserService userService, GarageService garageService, ReservationService reservationService) {
        this.userService = userService;
        this.garageService = garageService;
        this.reservationService = reservationService;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ModelAndView listReservation(HttpServletRequest request) {

        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();

        Long userId = (Long) session.getAttribute("ID");

        if(userId == null) {
            return new ModelAndView("redirect:/login");
        }

        User user = userService.get(userId);
        List<Reservation> reservations = reservationService.obtenerReservasByUserId(userId);

        List<Garage> garages = garageService.getAll();
        Garage garage = garages.get(0);

        model.put("username", user.getName());
        model.put("garage", garage);
        model.put("reservations", reservations);

        return new ModelAndView("my-reservation", model);
    }

    @RequestMapping("/start")
    public ModelAndView goToPreReservation(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        List<String> totalHoursList = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00");

        List<Garage> garages = garageService.getAll();
        Garage garage = garages.get(0);

        Long userId = (Long) request.getSession().getAttribute("ID");

        if(userId == null) {
            return new ModelAndView("redirect:../login");
        }

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setGarageId(garage.getId());
        reservationDTO.setUserId(userId);

        model.put("totalHours", totalHoursList);
        model.put("reservation", reservationDTO);

        return new ModelAndView("pre-reservation", model);
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public ModelAndView confirmReservation(@ModelAttribute("reservation") ReservationDTO reservationDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Garage garage = garageService.findById(reservationDTO.garageId);

        model.put("garage", garage);
        model.put("reservation", reservationDTO);

        return new ModelAndView("confirm-reservation", model);
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute("reservation") ReservationDTO reservationDTO, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        try {
            reservationService.addReservation(reservationDTO);
        } catch (GarageNotFoundException e) {
            List<Garage> garages = garageService.getAll();
            Garage garage = garages.get(0);
            model.put("garage", garage);
            model.put("reservation", reservationDTO);
            model.put("error", "Error al intentar guardar la reserva. Por favor, intente nuevamente");
            return new ModelAndView("confirm-reservation", model);
        } catch (UserNotFoundException e) {
            return new ModelAndView("redirect:../login");
        }

        return new ModelAndView("redirect:/reservations/list");
    }
}
