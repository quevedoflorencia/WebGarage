package com.tallerwebi.dominio;
import com.tallerwebi.presentacion.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
