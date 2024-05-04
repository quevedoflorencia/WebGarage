package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioLogin")
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService {

    private ReservationRespository reservationRespository;

    @Autowired
    public AvailabilityServiceImpl(ReservationRespository reservationRespository){
        this.reservationRespository = reservationRespository;
    }

    @Override
    public List getAvailableHours(String days) {
        List reservedDays = reservationRespository.reservationByDate(days);
        List availableHours = hoursAvailableThatDay(reservedDays, days);
        return availableHours;
    }

    private List hoursAvailableThatDay(List reservedDays, String days) {
        List hours = null;

        return hours;
    }


}

