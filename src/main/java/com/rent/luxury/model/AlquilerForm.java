package com.rent.luxury.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;

/**
 * Clase que representa el formulario de alquiler.
 */
public class AlquilerForm {
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"dd-MM-yyyy", "dd/MM/yyyy"})
    private Date fechaRecogida;
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd", fallbackPatterns = {"dd-MM-yyyy", "dd/MM/yyyy"})
    private Date fechaEntrega;
    
    @NotNull
    private Estados estado;
    
    @NotNull
    private Usuarios usuarios;
    
    @NotNull
    private Vehiculos vehiculo;

    /**
     * Obtiene la fecha de recogida del formulario.
     * 
     * @return Fecha de recogida
     */
    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    /**
     * Establece la fecha de recogida en el formulario.
     * 
     * @param fechaRecogida Fecha de recogida a establecer
     */
    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    /**
     * Obtiene la fecha de entrega del formulario.
     * 
     * @return Fecha de entrega
     */
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * Establece la fecha de entrega en el formulario.
     * 
     * @param fechaEntrega Fecha de entrega a establecer
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    


    public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	/**
     * Obtiene los usuarios del formulario.
     * 
     * @return Usuarios
     */
    public Usuarios getUsuarios() {
        return usuarios;
    }

    /**
     * Establece los usuarios en el formulario.
     * 
     * @param usuarios Usuarios a establecer
     */
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Obtiene el vehículo del formulario.
     * 
     * @return Vehículo
     */
    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    /**
     * Establece el vehículo en el formulario.
     * 
     * @param vehiculo Vehículo a establecer
     */
    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }
}
