package com.tallerwebi.dominio.model;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.List;


@Entity
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private String latitud;
    private String longitud;
    private String rutaFoto;

    @OneToMany(mappedBy = "garage")
    private List<Calificacion> calificaciones;

    private Double promedio;

    @OneToMany(mappedBy="garage", fetch = FetchType.EAGER)
    private List<GarageTipoVehiculo> garageTipoVehiculos;

    public Garage() {}

    public Garage(Integer id, String nombre, LocalTime horarioApertura, LocalTime horarioCierre, String latitud, String longitud, String rutaFoto) {
        this.id = id;
        this.nombre = nombre;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.rutaFoto = rutaFoto;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public LocalTime getHorarioApertura() {
        return horarioApertura;
    }

    public void setHorarioApertura(LocalTime openingTime) {
        this.horarioApertura = openingTime;
    }

    public LocalTime getHorarioCierre() {
        return horarioCierre;
    }

    public void setHorarioCierre(LocalTime closingTime) {
        this.horarioCierre = closingTime;
    }

    public List<GarageTipoVehiculo> getGarageTipoVehiculos() { return garageTipoVehiculos; }

    public void setGarageTipoVehiculos(List<GarageTipoVehiculo> garageTipoVehiculos) { this.garageTipoVehiculos = garageTipoVehiculos; }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String latitud) {
        this.longitud = longitud;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public String getPromedioFormateado() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(this.promedio);
    }

}
