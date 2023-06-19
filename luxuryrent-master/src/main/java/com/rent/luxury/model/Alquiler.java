package com.rent.luxury.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"dd-MM-yyyy", "dd/MM/yyyy"})
    private Date fechaReserva;
    
    @Column(nullable = false)
    private double precio;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuarios usuarios;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    @JsonIgnore
    private Estados estado;
    
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "vehiculo_id", referencedColumnName = "id"),
        @JoinColumn(name = "vehiculo_km", referencedColumnName = "kilometraje"),
    })
    @JsonIgnore
    private Vehiculos vehiculo;

    /**
     * Crea una instancia de Alquiler con los datos proporcionados.
     *
     * @param fechaRecogida Fecha de recogida del vehículo.
     * @param fechaEntrega  Fecha de entrega del vehículo.
     * @param usuarios      Usuario asociado al alquiler.
     * @param estado        Estado del alquiler.
     * @param vehiculo      Vehículo asociado al alquiler.
     */
    public Alquiler(Date fechaRecogida, Date fechaEntrega, Usuarios usuarios, Estados estado, Vehiculos vehiculo) {
        this.fechaRecogida = fechaRecogida;
        this.fechaEntrega = fechaEntrega;
        this.fechaReserva = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.usuarios = usuarios;
        this.estado = estado;
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
    /**
     * Devuelve el ID del alquiler.
     *
     * @return ID del alquiler.
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Establece el ID del alquiler.
     *
     * @param id ID del alquiler.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Devuelve la fecha de recogida del vehículo.
     *
     * @return Fecha de recogida del vehículo.
     */
    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    /**
     * Establece la fecha de recogida del vehículo.
     *
     * @param fechaRecogida Fecha de recogida del vehículo.
     */
    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    /**
     * Devuelve la fecha de entrega del vehículo.
     *
     * @return Fecha de entrega del vehículo.
     */
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * Establece la fecha de entrega del vehículo.
     *
     * @param fechaEntrega Fecha de entrega del vehículo.
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * Devuelve la fecha de reserva del alquiler.
     *
     * @return Fecha de reserva del alquiler.
     */
    public Date getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Establece la fecha de reserva del alquiler.
     *
     * @param fechaReserva Fecha de reserva del alquiler.
     */
    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    /**
     * Devuelve el precio del alquiler.
     *
     * @return Precio del alquiler.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del alquiler.
     *
     * @param precio Precio del alquiler.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve el estado del alquiler.
     *
     * @return Estado del alquiler.
     */
    public Estados getEstado() {
        return estado;
    }

    /**
     * Establece el estado del alquiler.
     *
     * @param estado Estado del alquiler.
     */
    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    /**
     * Devuelve el usuario asociado al alquiler.
     *
     * @return Usuario asociado al alquiler.
     */
    public Usuarios getUsuarios() {
        return usuarios;
    }

    /**
     * Establece el usuario asociado al alquiler.
     *
     * @param usuarios Usuario asociado al alquiler.
     */
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Devuelve el vehículo asociado al alquiler.
     *
     * @return Vehículo asociado al alquiler.
     */
    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    /**
     * Establece el vehículo asociado al alquiler.
     *
     * @param vehiculo Vehículo asociado al alquiler.
     */
    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto Alquiler.
     *
     * @return Representación en forma de cadena del objeto Alquiler.
     */
    @Override
    public String toString() {
        return "Alquiler [fechaRecogida=" + fechaRecogida + ", fechaEntrega=" + fechaEntrega + ", fechaReserva="
                + fechaReserva + ", precio=" + precio + ", estado=" + estado + ", usuarios=" + usuarios + ", vehiculo="
                + vehiculo + "]";
    }
}