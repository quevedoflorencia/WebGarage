package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
@Table(name = "estado_reserva")
public class EstadoReserva {

    public static final Integer PENDIENTE = 1;
	public static final Integer CONFIRMADA = 2;
	public static final Integer PAGADA = 3;
	public static final Integer ACTIVA = 4;
	public static final Integer CANCELADA = 5;
	public static final Integer VENCIDA = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;

    public EstadoReserva() {}

    public EstadoReserva(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoReserva(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
