package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcepcionGarageNoExiste;
import com.tallerwebi.dominio.excepcion.ExcepcionUsuarioNoEncontrado;
import com.tallerwebi.dominio.model.*;
import com.tallerwebi.presentacion.dto.ReservaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service("servicioReserva")
@Transactional
public class ServicioReservaImpl implements ServicioReserva {

    private RepositorioReserva repositorioReserva;
    private ServicioGarage servicioGarage;
    private ServicioUsuario servicioUsuario;
    private ServicioGarageTipoVehiculo servicioGarageTipoVehiculo;
    private ServicioEstadoReserva servicioEstadoReserva;

    @Autowired
    public ServicioReservaImpl(
            RepositorioReserva repositorioReserva,
            ServicioGarage servicioGarage,
            ServicioUsuario servicioUsuario,
            ServicioGarageTipoVehiculo servicioGarageTipoVehiculo,
            ServicioEstadoReserva servicioEstadoReserva
    ) {
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

        EstadoReserva estadoInicial = servicioEstadoReserva.obtenerPorId(EstadoReserva.CONFIRMADA);

        LocalDateTime ahora = LocalDateTime.now();

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
                estadoInicial,
                ahora

        );

        repositorioReserva.guardar(reserva);
        return reserva;
    }

    @Override
    public List traerHorasOcupadas(String day) {
        List reservas = repositorioReserva.reservasPorFecha(day);
        Integer capacidad = 1;
        return horasOcupadasEseDia(reservas, capacidad);
    }

    @Override
    public List<Reserva> obtenerReservasByUserId(Long id) {
        return repositorioReserva.obtenerPorUserId(id);
    }

    @Override
    public List<Reserva> traerPorEstado(int estadoId) {

        return repositorioReserva.obtenerImpagas(estadoId);
    }

    @Override
    public Reserva buscarPorId(Long reservaId) {
        return repositorioReserva.obtenerPorId(reservaId);
    }

    @Override
    public void activar(Long reservaId) {
        Reserva reserva = repositorioReserva.obtenerPorId(reservaId);
        EstadoReserva estadoActivada = servicioEstadoReserva.obtenerPorId(EstadoReserva.ACTIVA);
        reserva.setEstado(estadoActivada);
        repositorioReserva.actualizar(reserva);
    }

    @Override
    public void cancelar(Long reservaId) {
        Reserva reserva = repositorioReserva.obtenerPorId(reservaId);
        EstadoReserva estadoCancelada = servicioEstadoReserva.obtenerPorId(EstadoReserva.CANCELADA);
        reserva.setEstado(estadoCancelada);
        repositorioReserva.actualizar(reserva);
    }

    @Override
    public void validarVencimientoReservas(List<Reserva> reservas) {
        for (Reserva reserva : reservas) {
            if(estaVencida(reserva)){
                EstadoReserva estadoVencida = servicioEstadoReserva.obtenerPorId(EstadoReserva.VENCIDA);
                reserva.setEstado(estadoVencida);
                repositorioReserva.actualizar(reserva);
            }
        }
    }

    @Override
    public void pagar(Reserva reserva) {
        EstadoReserva estadoPagada = servicioEstadoReserva.obtenerPorId(EstadoReserva.PAGADA);
        reserva.setEstado(estadoPagada);
        repositorioReserva.actualizar(reserva);
    }

    @Override
    public boolean estaVencida(Reserva reserva) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaReserva = LocalDate.parse(reserva.getDia(),formatter);
        LocalDate fechaActual = LocalDate.now();

        if(fechaReserva.isBefore(fechaActual)) {
            return true;
        }
        return false;
    }

    @Override
    public Collection<String> traerHorasCierre(Integer garageId) {
        Garage garage = servicioGarage.buscarPorId(garageId);
        return horariosAListaDeHoras(garage.getHorarioApertura(), garage.getHorarioCierre());
    }

    private List horariosAListaDeHoras(LocalTime horarioApertura, LocalTime horarioCierre) {
        List activeHours = IntStream.rangeClosed(horarioApertura.getHour(),horarioCierre.getHour()).mapToObj(Integer::toString).collect(Collectors.toList());
        List hours = IntStream.rangeClosed(0,23).mapToObj(Integer::toString).collect(Collectors.toList());

        return (List) hours.stream().filter(hora->!activeHours.contains(hora)).collect(Collectors.toList());
    }


    @Override
    public Collection<String> traerHorasOcupadasPorDiaYTipoVehiculo(String selectedDate, Integer garageTipoVehiculoId) {
        GarageTipoVehiculo garageTipoVehiculo = servicioGarageTipoVehiculo.obtenerPorId(garageTipoVehiculoId);
        List reservas = repositorioReserva.reservasPorFechaYTipoDeAuto(selectedDate,garageTipoVehiculoId);
        return horasOcupadasEseDia(reservas, garageTipoVehiculo.getCapacidad());
    }

    private List horasOcupadasEseDia(List reservas, Integer capacidad) {
        List horasOcupadas = new ArrayList();
        Map spotsPorCadaHora = new HashMap();

         horasSinCupoSegunReservas(reservas,spotsPorCadaHora, horasOcupadas, capacidad);

        return horasOcupadas;
    }

    private void horasSinCupoSegunReservas(List reservas, Map spotsPorCadaHora, List horasOcupadas, Integer capacidad) {
        for (Object obj: reservas
        ) {
            Reserva reserva = (Reserva) obj;

            recorreCadaHoraDeLaReservaYLaContabiliza(reserva, spotsPorCadaHora, horasOcupadas, capacidad);

        }
    }

    private void recorreCadaHoraDeLaReservaYLaContabiliza(Reserva reserva, Map spotsPorCadaHora, List horasOcupadas, Integer capacidad) {
        int primerHora = traeHoraComoEntero(reserva.getHorarioInicio());
        int ultimaHora= traeHoraComoEntero(reserva.getHorarioFin());

        for(int hora=primerHora;
            hora < ultimaHora;hora++){

            int auxParaContabilizar;

            if(spotsPorCadaHora.get(hora)==null){
                spotsPorCadaHora.put(hora,1);
            }else {
                auxParaContabilizar = (int) spotsPorCadaHora.get(hora);
                spotsPorCadaHora.put(hora, auxParaContabilizar++);
            }

            if(validarHoraOcupada(hora, capacidad, horasOcupadas, spotsPorCadaHora)){
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
