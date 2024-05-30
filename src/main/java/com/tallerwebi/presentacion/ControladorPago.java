package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.presentacion.dto.DatosLoginDTO;
import com.tallerwebi.presentacion.dto.DatosPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping("/pago")
public class ControladorPago {

    private ServicioPago servicioPago;
    private ServicioReserva servicioReserva;

    @Autowired
    public ControladorPago(ServicioPago servicioPago, ServicioReserva servicioReserva) {
        this.servicioPago = servicioPago;
        this.servicioReserva = servicioReserva;
    }

    @RequestMapping("/formulario-pago/{id}")
    public ModelAndView irPago(@PathVariable("id") Long reservaId, HttpServletRequest request) {

        ModelMap modelo = new ModelMap();
        Reserva reserva = servicioReserva.buscarPorId(reservaId);
        DatosPagoDTO pagoData= new DatosPagoDTO();
        pagoData.setIdReserva(reservaId);
        modelo.put("pagoData", pagoData);
        //return new ModelAndView("formulario", modelo);
        return new ModelAndView("formulario-pago", modelo);
    }

    @RequestMapping(path = "/validar", method = RequestMethod.POST)
    public ModelAndView validarPago(@ModelAttribute("pagoData") DatosPagoDTO datosPagoDTO) {
        ModelMap model = new ModelMap();

        Boolean  resultadoValidacionNumero = servicioPago.validarNumeroTarjeta(datosPagoDTO.getNumeroTarjeta());
        Boolean resultadoValidacionCvv = servicioPago.validarCvv(datosPagoDTO.getCvv());
        if(resultadoValidacionNumero && resultadoValidacionCvv ){

            servicioPago.registrarPago(datosPagoDTO);
            model.put("exito", "Su pago ha sido exitoso, te esperamos!");
            Reserva reserva= servicioReserva.buscarPorId(datosPagoDTO.getIdReserva());
            model.put("reserva" , reserva);
            return new ModelAndView("pago-exitoso", model);
        }else {
            model.put("error", "Número de Tarjeta o CVV Inválido");

        }
        return new ModelAndView("formulario-pago", model);
    }

    /*
    @RequestMapping(path = "/validar", method = RequestMethod.POST)
    public ModelAndView validarPago(@ModelAttribute("pagoData") DatosPagoDTO datosPagoDTO, @PathVariable("id") Long reservaId, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Reserva reserva = servicioReserva.buscarPorId(reservaId);
        // String titularTarjeta= datosPagoDTO.getTitularTarjeta();
        Long numeroTarjeta = datosPagoDTO.getNumeroTarjeta();
        //  LocalDate fechaVencimiento = datosPagoDTO.getFechaVencimiento();


        Boolean resultadoValidacion = servicioPago.validarNumeroTarjeta(numeroTarjeta);

        if (resultadoValidacion == true) {
            servicioPago.registrarPago(datosPagoDTO, reserva);
            model.put("exitoso", "Pago exitoso!");
        } else {
            model.put("error", "Número de Tarjeta Inválido");
        }
        return new ModelAndView("mostrar-mensaje", model);
    }
    */

}














