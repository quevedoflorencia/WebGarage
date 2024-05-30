package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.excepcion.ExcepcionCvvTarjetaInvalida;
import com.tallerwebi.dominio.excepcion.ExcepcionNumeroTarjetaInvalida;
import com.tallerwebi.dominio.excepcion.ExcepcionReservaNoExiste;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.presentacion.dto.DatosPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView irPago(@PathVariable("id") Long reservaId) {

        ModelMap modelo = new ModelMap();

        DatosPagoDTO pagoData= new DatosPagoDTO();

        pagoData.setIdReserva(reservaId);
        modelo.put("pagoData", pagoData);

        return new ModelAndView("formulario-pago", modelo);
    }

    @RequestMapping(path = "/validar", method = RequestMethod.POST)
    public ModelAndView validarPago(@ModelAttribute("pagoData") DatosPagoDTO datosPagoDTO) {
        ModelMap model = new ModelMap();

        try {
            Reserva reserva = servicioReserva.buscarPorId(datosPagoDTO.getIdReserva());

            if(reserva == null) {
                throw new ExcepcionReservaNoExiste();
            }

            servicioPago.validarTarjeta(datosPagoDTO.getNumeroTarjeta(), datosPagoDTO.getCvv());

            servicioPago.registrarPago(reserva);

            model.put("exito", "¡Su pago ha sido exitoso, te esperamos!");
            model.put("reserva", reserva);

            return new ModelAndView("pago-exitoso", model);

        } catch (ExcepcionReservaNoExiste e) {
            model.put("error", "Hubo un inconveniente, por favor intente nuevamente");
        } catch (ExcepcionNumeroTarjetaInvalida e) {
            model.put("error", "Número de Tarjeta inválida");
        } catch (ExcepcionCvvTarjetaInvalida e) {
            model.put("error", "CVV Inválido");
        }

        return new ModelAndView("formulario-pago", model);
    }
}
