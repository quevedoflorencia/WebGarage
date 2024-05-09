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
    public List getReservedHours(String day) {
        List reservations= reservationRepository.reservationByDate(day);
        return hoursReservedThatDay(reservations, day);
    }

    @Override
    public Reservation getReservationByUserId(Long id) {
        return reservationRepository.reservationByIdUser(id);
    }

    @Override
    public List<Reservation> obtenerReservasByUserId(Long id) {
        return reservationRepository.obtenerReservasByUserId(id);
    }

    private List hoursReservedThatDay(List reservations, String days) {
        List fullHours = new ArrayList();
        Map countingHours = new HashMap();

        //recorre las reservas y extrae las horas
        for (Object obj: reservations
        ) {
            Reservation res = (Reservation) obj;
            String startTime = Arrays.stream(res.getStartTime().split(":"))
                    .findFirst().orElse(null);
            String finishTime=Arrays.stream(res.getFinishTime().split(":"))
                    .findFirst().orElse(null);

            //recorre las horas una a una y las contabiliza en el mapa
            for(int x=Integer.parseInt(startTime);
                x < Integer.parseInt(finishTime);x++){

                int prevMapValue =0;

                //en determinado horario, de no existir arranca el contador en uno
                if(countingHours.get(x)==null){
                    countingHours.put(x,1);
                }else {
                    //de existir, lo sumamos
                    //pero si el contador ya llego a los 10 lo sumamos a la lista
                    prevMapValue = (int) countingHours.get(x);
                    countingHours.put(x, prevMapValue++);
                }

                if(countingHours.get(x)!=null && (int)countingHours.get(x) ==1){ //todo cuando tengamos el limite de capacidad en una variable aparte, modificarla
                    //todo ya se que se puede optimizar los if
                    //pero para que sea mas entendible, aca validamos que el horario a ingresar en la lista
                    //de horas ocupadas no haya sido ya registrado.
                    if(fullHours!=null && !fullHours.contains(String.valueOf(x))){
                        fullHours.add(String.valueOf(x));
                    }

                }
            }

        }

        return fullHours;
    }

}
