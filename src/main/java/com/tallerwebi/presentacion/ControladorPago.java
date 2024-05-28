package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pago")
public class ControladorPago {
    private ServicioPago servicioPago;

    @Autowired
    public ControladorPago(ServicioPago servicioPago) {

        this.servicioPago = servicioPago;
    }


    @RequestMapping("/formulario-pago/{id}")
        public ModelAndView mostrarFormulario(@PathVariable("id") Long reservaId, HttpServletRequest request){

        ModelMap model = new ModelMap();

        model.put("idReserva", reservaId);
        return new ModelAndView("formulario-pago", model);
    }


    @RequestMapping(path = "/procesar-pago", method = RequestMethod.POST)
        public ModelAndView procesarPago(@ModelAttribute("datosPago") DatosPagoDTO datosPagoDTO, HttpServletRequest request){
        ModelMap model = new ModelMap();

        String numero = datosPagoDTO.getNumeroTarjeta();

        Boolean resultadoValidacionTarjeta = servicioPago.validarNumeroTarjeta(numero);


        return new ModelAndView("redirect:/home");

    }


}
