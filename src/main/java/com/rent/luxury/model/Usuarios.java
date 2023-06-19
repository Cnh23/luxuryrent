package com.rent.luxury.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Clase que representa a un usuario en el sistema.
 */
@Entity
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellidos;
    
    @Column(nullable = false, unique = true)
    private String dni;
    
    @Column(nullable = false)
    private String direccion;
    
    @Column(nullable = false, unique = true)
    private String telefono;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;
    
    @OneToMany(mappedBy="usuarios", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Alquiler> listaAlquiler;
    
    /**
     * Constructor de la clase Usuarios.
     * 
     * @param nombre     el nombre del usuario.
     * @param apellidos  los apellidos del usuario.
     * @param dni        el DNI del usuario.
     * @param direccion  la dirección del usuario.
     * @param telefono   el número de teléfono del usuario.
     * @param email      el correo electrónico del usuario.
     * @param password   la contraseña del usuario.
     * @param role       el rol del usuario.
     */
    public Usuarios(String nombre, String apellidos, String dni, String direccion, String telefono, String email, String password, Role role) {
        super();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    /**
     * Constructor sin argumentos de la clase Usuarios.
     */
    public Usuarios() {
        
    }

    /**
     * Obtiene el ID del usuario.
     * 
     * @return el ID del usuario.
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Establece el ID del usuario.
     * 
     * @param id el ID del usuario.
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * Obtiene el número de teléfono del usuario.
     * 
     * @return el número de teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del usuario.
     * 
     * @param telefono el número de teléfono del usuario.
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

    /**
     * Obtiene la lista de alquileres asociados al usuario.
     * 
     * @return la lista de alquileres asociados al usuario.
     */
    public List<Alquiler> getListaAlquiler() {
        return listaAlquiler;
    }

    /**
     * Establece la lista de alquileres asociados al usuario.
     * 
     * @param listaAlquiler la lista de alquileres asociados al usuario.
     */
    public void setListaAlquiler(List<Alquiler> listaAlquiler) {
        this.listaAlquiler = listaAlquiler;
    }

    @Override
    public String toString() {
        return String.format("Clientes [id=%s, nombre=%s, apellidos=%s, dni=%s, direccion=%s, telefono=%s, email=%s, password=%s]",
                id, nombre, apellidos, dni, direccion, telefono, email, password);
    }
}
