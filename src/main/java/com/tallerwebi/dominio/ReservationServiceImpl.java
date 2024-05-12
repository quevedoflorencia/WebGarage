package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.GarageNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.model.Garage;
import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.dominio.model.User;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("reservationService")
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private GarageService garageService;
    private UserService userService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, GarageService garageService, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.garageService = garageService;
        this.userService = userService;
    }

    @Override
    public void addReservation(ReservationDTO reservationDTO) throws GarageNotFoundException, UserNotFoundException {

        User user = userService.get(reservationDTO.userId);

        if(user == null) {
            throw new UserNotFoundException();
        }

        Garage garage = garageService.findById(reservationDTO.garageId);

        if(garage == null) {
            throw new GarageNotFoundException();
        }

        Reservation reservation = new Reservation(
                null,
                user,
                garage,
                reservationDTO.date,
                reservationDTO.startTime,
                reservationDTO.finishTime
        );

        reservationRepository.addNewReservation(reservation);
    }

    @Override
    public List traerHorasOcupadas(String day) {
        List reservations= reservationRepository.reservationByDate(day);
        return horasOcupadasEseDia(reservations);
    }

    @Override
    public Reservation getReservationByUserId(Long id) {
        return reservationRepository.reservationByIdUser(id);
    }

    @Override
    public List<Reservation> obtenerReservasByUserId(Long id) {
        return reservationRepository.obtenerReservasByUserId(id);
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
            Reservation reserva = (Reservation) obj;

            recorreCadaHoraDeLaReservaYLaContabiliza(reserva, spotsPorCadaHora, horasOcupadas);

        }
    }

    private void recorreCadaHoraDeLaReservaYLaContabiliza(Reservation reserva, Map spotsPorCadaHora, List horasOcupadas) {
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
