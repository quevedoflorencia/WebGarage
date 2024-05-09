package com.tallerwebi.dominio;
import com.tallerwebi.dominio.model.Reservation;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reservationService")
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) { this.reservationRepository = reservationRepository; }

    @Override
    public void addReservation(ReservationDTO reservationDTO) {
        reservationRepository.addNewReservation(reservationDTO);
    }

    @Override
    public List getReservedHours(String day) {
        List reservations= reservationRepository.reservationByDate(day);
        List fullHours = hoursReservedThatDay(reservations, day);
        return fullHours;
    }
    /*
        @Override
        public List getReservationByUserId(Long id) {

            List <Reservation> reservations= reservationRepository.reservationByIdUser(id);

            return reservations;
        }
    */
    @Override
    public Reservation getReservationByUserId(Long id) {

        Reservation reservations= reservationRepository.reservationByIdUser(id);

        return reservations;
    }

    @Override
    public List obtenerReservasByUserId(Long id) {
        List <Reservation> reservas= reservationRepository.obtenerReservasByUserId(id);
        return reservas;
    }

    private List hoursReservedThatDay(List reservations, String days) {
        List fullHours = new ArrayList();
        Map countingHours = new HashMap();

        //recorre las reservas y extrae las horas
        for (Object obj: reservations
        ) {
            Reservation res = (Reservation) obj;

            //recorre las horas una a una y las contabiliza en el mapa
            for(int x=Integer.parseInt(res.getStartTime());
                x < Integer.parseInt(res.getFinishTime());x++){

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
