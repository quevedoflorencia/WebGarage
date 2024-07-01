package com.tallerwebi.dominio.model;

import javax.persistence.*;


@Entity
@Table(name = "garage_tipo_vehiculo")
public class GarageTipoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_garage")
    private Garage garage;
    @ManyToOne
    @JoinColumn(name = "id_tipo_vehiculo")
    private TipoVehiculo tipoVehiculo;
    private Double precioHora;

    public GarageTipoVehiculo() {}

    public GarageTipoVehiculo(Integer id, Double precioHora, Garage garage, TipoVehiculo tipoVehiculo) {
        this.id = id;
        this.precioHora = precioHora;
        this.garage = garage;
        this.tipoVehiculo = tipoVehiculo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Double getPrecioHora() { return precioHora; }
    public void setPrecioHora(Double precioHora) { this.precioHora = precioHora; }

    public Garage getIdGarage() { return garage; }
    public void setGarage(Garage garage) { this.garage = garage; }

    public TipoVehiculo getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
}
