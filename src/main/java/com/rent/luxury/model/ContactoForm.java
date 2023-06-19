package com.rent.luxury.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContactoForm {

    @NotNull
    @Size(min=3, max=30, message="El campo nombre debe tener entre 3 y 30 caracteres")
    private String nombre;
	
    @NotNull
    @Email(message="Email debe ser valido")
    private String email;
    
    @NotNull
    private String mensaje;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    
    
}
