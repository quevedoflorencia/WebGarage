package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.GarageNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reservacion;
import com.tallerwebi.dominio.model.Usuario;
import com.tallerwebi.presentacion.dto.ReservacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("reservationService")
@Transactional
public class ServicioReservacionImpl implements ServicioRepositorio {

    private RepositorioReservacion repositorioReservacion;
    private ServicioGarage servicioGarage;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ServicioReservacionImpl(RepositorioReservacion repositorioReservacion, ServicioGarage servicioGarage, ServicioUsuario servicioUsuario) {
        this.repositorioReservacion = repositorioReservacion;
        this.servicioGarage = servicioGarage;
        this.servicioUsuario = servicioUsuario;
    }

    @Override
    public void addReservation(ReservacionDTO reservacionDTO) throws GarageNotFoundException, UserNotFoundException {

        Usuario usuario = servicioUsuario.get(reservacionDTO.userId);

        if(usuario == null) {
            throw new UserNotFoundException();
        }

        Garage garage = servicioGarage.findById(reservacionDTO.garageId);

        if(garage == null) {
            throw new GarageNotFoundException();
        }

        Reservacion reservacion = new Reservacion(
                null,
                usuario,
                garage,
                reservacionDTO.date,
                reservacionDTO.startTime,
                reservacionDTO.finishTime
        );

        repositorioReservacion.addNewReservation(reservacion);
    }

    @Override
    public List traerHorasOcupadas(String day) {
        List reservations= repositorioReservacion.reservationByDate(day);
        return horasOcupadasEseDia(reservations);
    }

    @Override
    public Reservacion getReservationByUserId(Long id) {
        return repositorioReservacion.reservationByIdUser(id);
    }

    @Override
    public List<Reservacion> obtenerReservasByUserId(Long id) {
        return repositorioReservacion.obtenerReservasByUserId(id);
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
            Reservacion reserva = (Reservacion) obj;

            recorreCadaHoraDeLaReservaYLaContabiliza(reserva, spotsPorCadaHora, horasOcupadas);

        }
    }

    private void recorreCadaHoraDeLaReservaYLaContabiliza(Reservacion reserva, Map spotsPorCadaHora, List horasOcupadas) {
        int primerHora = traeHoraComoEntero(reserva.getStartTime());
        int ultimaHora= traeHoraComoEntero(reserva.getFinishTime());

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
