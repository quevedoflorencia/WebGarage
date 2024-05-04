package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reservedService")
@Transactional
public class ReservedServiceImpl implements ReservedService {

    private ReservationRespository reservationRespository;

    @Autowired
    public ReservedServiceImpl(ReservationRespository reservationRespository){
        this.reservationRespository = reservationRespository;
    }

    @Override
    public List getReservedHours(String day) {
        List reservations= reservationRespository.reservationByDate(day);
        List fullHours = hoursReservedThatDay(reservations, day);
        return fullHours;
    }

    private List hoursReservedThatDay(List reservations, String days) {
        List fullHours = new ArrayList();
        Map countingHours = new HashMap();
        
        //recorre las reservas y extrae las horas
        for (Object obj: reservations
             ) {
            Reservation res = (Reservation) obj;
            
            //recorre las horas una a una y las contabiliza en el mapa
            for(int x=Integer.getInteger(res.getStartTime());
                x < Integer.getInteger(res.getFinishTime());x++){

                //en determinado horario, de no existir arranca el contador en uno
                if(countingHours.get(x)==null){
                    countingHours.put(x,1);
                }else {
                    //de existir, lo sumamos
                    //pero si el contador ya llego a los 10 lo sumamos a la lista
                    int prevMapValue = (int) countingHours.get(x);
                    if(prevMapValue==10){ //todo cuando tengamos el limite de capacidad en una variable aparte, modificarla
                        //todo ya se que se puede optimizar los if
                        //pero para que sea mas entendible, aca validamos que el horario a ingresar en la lista
                        //de horas ocupadas no haya sido ya registrado.
                        if(fullHours!=null && !fullHours.contains(x)){
                            fullHours.add(x);
                        }

                    }else {
                        countingHours.put(x, prevMapValue++);
                    }
                }
            }

        }

        return fullHours;
    }


}

