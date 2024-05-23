package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoEncontrado;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reserva;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.presentacion.dto.ReservaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("servicioReserva")
@Transactional
public class ServicioReservaImpl implements ServicioReserva {

    private RepositorioReserva repositorioReserva;
    private ServicioGarage servicioGarage;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ServicioReservaImpl(RepositorioReserva repositorioReserva, ServicioGarage servicioGarage, ServicioUsuario servicioUsuario) {
        this.repositorioReserva = repositorioReserva;
        this.servicioGarage = servicioGarage;
        this.servicioUsuario = servicioUsuario;
    }

    @Override
    public void agregarReserva(ReservaDTO reservaDTO) throws ExcepcionGarageNoEncontrado, ExcepcionUsuarioNoEncontrado {

        Usuario usuario = servicioUsuario.get(reservaDTO.userId);

        if(usuario == null) {
            throw new ExcepcionUsuarioNoEncontrado();
        }

        Garage garage = servicioGarage.buscarPorId(reservaDTO.garageId);

        if(garage == null) {
            throw new ExcepcionGarageNoEncontrado();
        }

        Reserva reserva = new Reserva(
                null,
                usuario,
                garage,
                reservaDTO.dia,
                reservaDTO.horarioInicio,
                reservaDTO.horarioFin
        );

        repositorioReserva.agregarNuevaReserva(reserva);
    }

    @Override
    public List traerHorasOcupadas(String day) {
        List reservas = repositorioReserva.reservasPorFecha(day);
        return horasOcupadasEseDia(reservas);
    }

    @Override
    public List<Reserva> obtenerReservasByUserId(Long id) {
        return repositorioReserva.obtenerReservasByUserId(id);
    }

    private List horasOcupadasEseDia(List reservas) {
        List horasOcupadas = new ArrayList();
        Map spotsPorCadaHora = new HashMap();

         horasSinCupoSegunReservas(reservas,spotsPorCadaHora, horasOcupadas);

        return horasOcupadas;
    }

    private void horasSinCupoSegunReservas(List reservas, Map spotsPorCadaHora, List horasOcupadas) {
        for (Object obj: reservas
        ) {
            Reserva reserva = (Reserva) obj;

            recorreCadaHoraDeLaReservaYLaContabiliza(reserva, spotsPorCadaHora, horasOcupadas);

        }
    }

    private void recorreCadaHoraDeLaReservaYLaContabiliza(Reserva reserva, Map spotsPorCadaHora, List horasOcupadas) {
        int primerHora = traeHoraComoEntero(reserva.getHorarioInicio());
        int ultimaHora= traeHoraComoEntero(reserva.getHorarioFin());

        int capacidadDeGarage = 1;



        for(int hora=primerHora;
            hora < ultimaHora;hora++){

            int auxParaContabilizar;

            if(spotsPorCadaHora.get(hora)==null){
                spotsPorCadaHora.put(hora,1);
            }else {
                auxParaContabilizar = (int) spotsPorCadaHora.get(hora);
                spotsPorCadaHora.put(hora, auxParaContabilizar++);
            }

            if(validarHoraOcupada(hora, capacidadDeGarage, horasOcupadas, spotsPorCadaHora)){
                horasOcupadas.add(String.valueOf(hora));
            }
        }
    }

    private boolean validarHoraOcupada(int hora, int capacidadDeGarage, List horasOcupadas, Map spotsPorCadaHora) {
        return validarHoraLlena(hora, capacidadDeGarage, spotsPorCadaHora) && validarAgregarlaPorUnicaVez(horasOcupadas,hora);
    }

    private boolean validarAgregarlaPorUnicaVez(List horasOcupadas, int hora) {
        return horasOcupadas!=null && !horasOcupadas.contains(String.valueOf(hora));
    }

    private boolean validarHoraLlena(int hora, int capacidadDeGarage, Map spotsPorCadaHora) {
        return spotsPorCadaHora.get(hora)!=null && (int)spotsPorCadaHora.get(hora) == capacidadDeGarage;
    }


    private int traeHoraComoEntero(String time) {
        return Integer.parseInt(Arrays.stream(time.split(":"))
                .findFirst().orElse("0"));
    }

}
