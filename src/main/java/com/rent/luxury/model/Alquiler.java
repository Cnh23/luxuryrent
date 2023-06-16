package com.rent.luxury.model;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

/**
 * La clase Alquiler representa la información de un alquiler de vehículo.
 */
@Entity
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"dd-MM-yyyy", "dd/MM/yyyy"})
    private Date fechaRecogida;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"dd-MM-yyyy", "dd/MM/yyyy"})
    private Date fechaEntrega;

    @Column(nullable = false)
    private double precio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuarios;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "vehiculo_id", referencedColumnName = "id"),
        @JoinColumn(name = "vehiculo_km", referencedColumnName = "kilometraje")
    })
    private Vehiculos vehiculo;

    /**
     * Crea una instancia de la clase Alquiler con los parámetros proporcionados.
     * Calcula automáticamente el precio del alquiler basado en la diferencia entre las fechas de recogida y entrega.
     *
     * @param fechaRecogida La fecha de recogida del vehículo.
     * @param fechaEntrega  La fecha de entrega del vehículo.
     * @param usuarios      El objeto Usuarios asociado al alquiler.
     * @param vehiculo      El objeto Vehiculos asociado al alquiler.
     */
    public Alquiler(Date fechaRecogida, Date fechaEntrega, Usuarios usuarios, Vehiculos vehiculo) {
        this.fechaRecogida = fechaRecogida;
        this.fechaEntrega = fechaEntrega;
        this.usuarios = usuarios;
        this.vehiculo = vehiculo;
        int diasAlquiler = (int) ChronoUnit.DAYS.between(this.fechaRecogida.toInstant(), this.fechaEntrega.toInstant());
        this.precio = diasAlquiler * vehiculo.getPreciopordia();
    }

    /**
     * Crea una instancia vacía de la clase Alquiler.
     */
    public Alquiler() {
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }
}
