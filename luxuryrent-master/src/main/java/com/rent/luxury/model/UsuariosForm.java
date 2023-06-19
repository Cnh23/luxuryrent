package com.rent.luxury.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Clase que representa el formulario de registro de usuarios.
 */
public class UsuariosForm {
    @NotNull
    @Size(min=3, max=30, message="El campo nombre debe tener entre 3 y 30 caracteres")
    private String nombre;
    
    @NotNull
    @Size(min=3, max=30, message="El campo apellidos debe tener entre 3 y 30 caracteres")
    private String apellidos;
    
    @NotNull
    @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "El DNI debe tener 8 dígitos y una letra mayúscula al final.")
    private String dni;
    
    @NotNull
    private String direccion;
    
    @Pattern(regexp = "^\\+?[0-9]{9,}$", message = "El teléfono debe tener al menos 9 dígitos.")
    private String telefono;
    
    @NotNull
    @Email(message="Email debe ser valido")
    private String email;
    
    @NotNull
    @Size(min = 8, max = 30, message = "La contraseña debe tener entre 8 y 30 caracteres.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "La contraseña debe tener al menos una letra mayúscula, una letra minúscula y un dígito.")
    private String password;

    private Role role;
    
    /**
     * Obtiene el nombre del usuario.
     * 
     * @return el nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * 
     * @param nombre el nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     * 
     * @return los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     * 
     * @param apellidos los apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el DNI del usuario.
     * 
     * @return el DNI del usuario.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del usuario.
     * 
     * @param dni el DNI del usuario.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    /**
     * Obtiene el DNI del usuario.
     * 
     * @return el DNI del usuario.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece el DNI del usuario.
     * 
     * @param dni el DNI del usuario.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el teléfono del usuario.
     * 
     * @return el teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del usuario.
     * 
     * @param telefono el teléfono del usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * 
     * @return el correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     * 
     * @param email el correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return la contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param password la contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     * 
     * @return el rol del usuario.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     * 
     * @param role el rol del usuario.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
