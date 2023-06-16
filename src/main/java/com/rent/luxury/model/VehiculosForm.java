package com.rent.luxury.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * Clase utilizada para representar el formulario de registro de vehículos.
 */
public class VehiculosForm {

    @NotNull
    @Size(min=3, max=30, message="El campo marca debe tener entre 3 y 30 caracteres")
    private String marca;

    @NotNull
    @Size(min=3, max=30, message="El campo modelo debe tener entre 3 y 30 caracteres")
    private String modelo;

    @NotNull
    private String tipo;

    @NotNull
    @Size(min = 7, max = 7, message = "La matrícula debe tener 7 caracteres")
    @Pattern(regexp = "[0-9]{4}[A-Z]{3}", message = "La matrícula debe tener el formato correcto (por ejemplo, AB1234CD)")
    private String matricula;

    @NotNull
    private double kilometraje;

    @NotNull
    private double preciopordia;

    @NotNull
    private Integer anio;

    @NotNull
    private String enlaceImagen;

    /**
     * Obtiene la marca del vehículo.
     *
     * @return La marca del vehículo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del vehículo.
     *
     * @param marca La marca del vehículo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del vehículo.
     *
     * @return El modelo del vehículo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del vehículo.
     *
     * @param modelo El modelo del vehículo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el tipo de vehículo.
     *
     * @return El tipo de vehículo.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de vehículo.
     *
     * @param tipo El tipo de vehículo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la matrícula del vehículo.
     *
     * @return La matrícula del vehículo.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del vehículo.
     *
     * @param matricula La matrícula del vehículo.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene el kilometraje del vehículo.
     *
     * @return El kilometraje del vehículo.
     */
    public double getKilometraje() {
        return kilometraje;
    }

    /**
     * Establece el kilometraje del vehículo.
     *
     * @param kilometraje El kilometraje del vehículo.
     */
    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    /**
     * Obtiene el precio por día de alquiler del vehículo.
     *
     * @return El precio por día de alquiler del vehículo.
     */
    public double getPreciopordia() {
        return preciopordia;
    }

    /**
     * Establece el precio por día de alquiler del vehículo.
     *
     * @param preciopordia El precio por día de alquiler del vehículo.
     */
    public void setPreciopordia(double preciopordia) {
        this.preciopordia = preciopordia;
    }

    /**
     * Obtiene el año del vehículo.
     *
     * @return El año del vehículo.
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * Establece el año del vehículo.
     *
     * @param anio El año del vehículo.
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * Obtiene el enlace de la imagen del vehículo.
     *
     * @return El enlace de la imagen del vehículo.
     */
    public String getEnlaceImagen() {
        return enlaceImagen;
    }

    /**
     * Establece el enlace de la imagen del vehículo.
     *
     * @param enlaceImagen El enlace de la imagen del vehículo.
     */
    public void setEnlaceImagen(String enlaceImagen) {
        this.enlaceImagen = enlaceImagen;
    }
}
