package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoExiste;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.presentacion.dto.ReservaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("servicioReserva")
@Transactional
public class ServicioReservaImpl implements ServicioReserva {

    private RepositorioReserva repositorioReserva;
    private ServicioGarage servicioGarage;
    private ServicioUsuario servicioUsuario;
    private ServicioGarageTipoVehiculo servicioGarageTipoVehiculo;
    private ServicioEstadoReserva servicioEstadoReserva;

    @Autowired
    public ServicioReservaImpl(RepositorioReserva repositorioReserva, ServicioGarage servicioGarage, ServicioUsuario servicioUsuario, ServicioGarageTipoVehiculo servicioGarageTipoVehiculo, ServicioEstadoReserva servicioEstadoReserva) {
        this.repositorioReserva = repositorioReserva;
        this.servicioGarage = servicioGarage;
        this.servicioUsuario = servicioUsuario;
        this.servicioGarageTipoVehiculo = servicioGarageTipoVehiculo;
        this.servicioEstadoReserva = servicioEstadoReserva;
    }

    @Override
    public Reserva agregarReserva(ReservaDTO reservaDTO) throws ExcepcionGarageNoExiste, ExcepcionUsuarioNoEncontrado {

        Usuario usuario = servicioUsuario.get(reservaDTO.userId);

        if(usuario == null) {
            throw new ExcepcionUsuarioNoEncontrado();
        }

        Garage garage = servicioGarage.buscarPorId(reservaDTO.garageId);

        GarageTipoVehiculo garageTipoVehiculo = servicioGarageTipoVehiculo.obtenerPorId(reservaDTO.garageTipoVehiculoId);

        EstadoReserva estadoInicial = servicioEstadoReserva.obtenerEstadoSegunDescripcion("Pendiente");

        if(garage == null) {
            throw new ExcepcionGarageNoExiste();
        }

        Reserva reserva = new Reserva(
                usuario,
                garage,
                garageTipoVehiculo,
                reservaDTO.dia,
                reservaDTO.horarioInicio,
                reservaDTO.horarioFin,
                reservaDTO.precio,
                estadoInicial

        );

        repositorioReserva.guardar(reserva);
        return reserva;
    }

    @Override
    public List traerHorasOcupadas(String day) {
        List reservas = repositorioReserva.reservasPorFecha(day);
        return horasOcupadasEseDia(reservas);
    }

    @Override
    public List<Reserva> obtenerReservasByUserId(Long id) {
        return repositorioReserva.obtenerPorUserId(id);
    }

    @Override
    public Reserva buscarPorId(Long reservaId) {
        return repositorioReserva.obtenerPorId(reservaId);
    }

    @Override
    public void cancelar(Long reservaId) {
        Reserva reserva = repositorioReserva.obtenerPorId(reservaId);
        reserva.setEstado(setearEstadoCancelado());
        repositorioReserva.actualizar(reserva);
    }

    private EstadoReserva setearEstadoCancelado() {
        return servicioEstadoReserva.obtenerEstadoSegunDescripcion("Cancelado");
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

    @Override
    public Double calcularPrecio(String horarioInicio, String horarioFin, GarageTipoVehiculo garageTipoVehiculo){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Parsear las cadenas a LocalTime
        LocalTime desde = LocalTime.parse(horarioInicio, formatter);
        LocalTime hasta = LocalTime.parse(horarioFin, formatter);

        // Calcular la duración entre las dos horas
        Duration duracion = Duration.between(desde, hasta);
        double horas = Math.ceil(duracion.toMinutes() / 60.0);

        double precioPorHora = garageTipoVehiculo.getPrecioHora();

        return horas * precioPorHora;
    }

}
