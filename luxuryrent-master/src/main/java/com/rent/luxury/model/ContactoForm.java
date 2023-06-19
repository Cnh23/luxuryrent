package com.rent.luxury.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * La clase ContactoForm representa un formulario de contacto.
 */
public class ContactoForm {

    @NotNull
    @Size(min=3, max=30, message="El campo nombre debe tener entre 3 y 30 caracteres")
    private String nombre;
	
    @NotNull
    @Email(message="Email debe ser valido")
    private String email;
    
    @NotNull
    private String mensaje;

    /**
     * Obtiene el nombre del contacto del formulario.
     * 
     * @return Nombre del contacto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del contacto en el formulario.
     * 
     * @param nombre Nombre del contacto a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el email del contacto del formulario.
     * 
     * @return Email del contacto
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del contacto en el formulario.
     * 
     * @param email Email del contacto a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el mensaje del contacto del formulario.
     * 
     * @return Mensaje del contacto
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje del contacto en el formulario.
     * 
     * @param mensaje Mensaje del contacto a establecer
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
