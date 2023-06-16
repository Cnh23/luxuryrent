package com.rent.luxury.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Clase que representa un vehículo en el sistema de alquiler de lujo.
 */
@Entity
public class Vehiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable = false)
    private String marca;
    
    @Column(nullable = false)
    private String modelo;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private String matricula;
    
    @Column(nullable = false)
    private double kilometraje;
    
    @Column(nullable = false)
    private double preciopordia;
    
    @Column(nullable = false)
    private Integer anio;
    
    @Column(nullable = false)
    private boolean alquilado = false;
    
    @Column(nullable = false)
    private String enlaceImagen;

    @OneToMany(mappedBy="vehiculo", cascade = CascadeType.REMOVE)
    private List<Alquiler> listaAlquiler;

    public Vehiculos(String marca, String modelo, String tipo, String matricula, double kilometraje,
            double preciopordia, Integer anio, String enlaceImagen) {
        super();
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.matricula = matricula;
        this.kilometraje = kilometraje;
        this.preciopordia = preciopordia;
        this.anio = anio;
        this.enlaceImagen = enlaceImagen;
    }

    public Vehiculos() {
    }

    /**
     * Obtiene el ID del vehículo.
     * 
     * @return el ID del vehículo.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del vehículo.
     * 
     * @param id el ID del vehículo.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la marca del vehículo.
     * 
     * @return la marca del vehículo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del vehículo.
     * 
     * @param marca la marca del vehículo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del vehículo.
     * 
     * @return el modelo del vehículo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del vehículo.
     * 
     * @param modelo el modelo del vehículo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el tipo de vehículo.
     * 
     * @return el tipo de vehículo.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de vehículo.
     * 
     * @param tipo el tipo de vehículo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la matrícula del vehículo.
     * 
     * @return la matrícula del vehículo.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del vehículo.
     * 
     * @param matricula la matrícula del vehículo.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene el kilometraje del vehículo.
     * 
     * @return el kilometraje del vehículo.
     */
    public double getKilometraje() {
        return kilometraje;
    }

    /**
     * Establece el kilometraje del vehículo.
     * 
     * @param kilometraje el kilometraje del vehículo.
     */
    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    /**
     * Obtiene el precio por día de alquiler del vehículo.
     * 
     * @return el precio por día de alquiler del vehículo.
     */
    public double getPreciopordia() {
        return preciopordia;
    }

    /**
     * Establece el precio por día de alquiler del vehículo.
     * 
     * @param preciopordia el precio por día de alquiler del vehículo.
     */
    public void setPreciopordia(double preciopordia) {
        this.preciopordia = preciopordia;
    }

    /**
     * Obtiene el año de fabricación del vehículo.
     * 
     * @return el año de fabricación del vehículo.
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * Establece el año de fabricación del vehículo.
     * 
     * @param anio el año de fabricación del vehículo.
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * Verifica si el vehículo está alquilado.
     * 
     * @return `true` si el vehículo está alquilado, `false` si no está alquilado.
     */
    public boolean isAlquilado() {
        return alquilado;
    }

    /**
     * Establece el estado de alquiler del vehículo.
     * 
     * @param alquilado `true` si el vehículo está alquilado, `false` si no está alquilado.
     */
    public void setAlquilado(boolean alquilado) {
        this.alquilado = alquilado;
    }

    /**
     * Obtiene el enlace a la imagen del vehículo.
     * 
     * @return el enlace a la imagen del vehículo.
     */
    public String getEnlaceImagen() {
        return enlaceImagen;
    }

    /**
     * Establece el enlace a la imagen del vehículo.
     * 
     * @param enlaceImagen el enlace a la imagen del vehículo.
     */
    public void setEnlaceImagen(String enlaceImagen) {
        this.enlaceImagen = enlaceImagen;
    }

    /**
     * Obtiene la lista de alquileres asociados al vehículo.
     * 
     * @return la lista de alquileres asociados al vehículo.
     */
    public List<Alquiler> getListaAlquiler() {
        return listaAlquiler;
    }

    /**
     * Establece la lista de alquileres asociados al vehículo.
     * 
     * @param listaAlquiler la lista de alquileres asociados al vehículo.
     */
    public void setListaAlquiler(List<Alquiler> listaAlquiler) {
        this.listaAlquiler = listaAlquiler;
    }

    @Override
    public String toString() {
        return "Vehiculos [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", tipo=" + tipo + ", matricula="
                + matricula + ", kilometraje=" + kilometraje + ", preciopordia=" + preciopordia + ", anio=" + anio
                + ", alquilado=" + alquilado + ", enlaceImagen=" + enlaceImagen + ", listaAlquiler=" + listaAlquiler
                + "]";
    }
}
