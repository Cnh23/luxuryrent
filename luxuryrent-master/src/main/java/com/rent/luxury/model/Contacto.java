package com.rent.luxury.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * La clase Contacto representa la información de un formulario de contacto.
 */
@Entity
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String mensaje;

    /**
     * Crea una instancia de la clase Contacto con los datos especificados.
     * 
     * @param nombre  Nombre del contacto
     * @param email   Email del contacto
     * @param mensaje Mensaje del contacto
     */
    public Contacto(String nombre, String email, String mensaje) {
        this.nombre = nombre;
        this.email = email;
        this.mensaje = mensaje;
    }

    /**
     * Crea una instancia vacía de la clase Contacto.
     */
    public Contacto() {
    }

    /**
     * Obtiene el ID del contacto.
     * 
     * @return ID del contacto
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del contacto.
     * 
     * @param id ID del contacto a establecer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del contacto.
     * 
     * @return Nombre del contacto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del contacto.
     * 
     * @param nombre Nombre del contacto a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el email del contacto.
     * 
     * @return Email del contacto
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del contacto.
     * 
     * @param email Email del contacto a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el mensaje del contacto.
     * 
     * @return Mensaje del contacto
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje del contacto.
     * 
     * @param mensaje Mensaje del contacto a establecer
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Contacto [id=" + id + ", nombre=" + nombre + ", email=" + email + ", mensaje=" + mensaje + "]";
    }
}
