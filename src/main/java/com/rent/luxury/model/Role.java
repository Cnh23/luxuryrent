package com.rent.luxury.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa un rol de usuario en el sistema.
 */
@Entity
@Table(name = "roles")
public class Role {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nombrerol;
    
    @OneToMany(mappedBy = "role")
    private List<Usuarios> usuarios;
    
    /**
     * Obtiene el ID del rol.
     * 
     * @return el ID del rol.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del rol.
     * 
     * @param id el ID del rol.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del rol.
     * 
     * @return el nombre del rol.
     */
    public String getNombrerol() {
        return nombrerol;
    }

    /**
     * Establece el nombre del rol.
     * 
     * @param nombrerol el nombre del rol.
     */
    public void setNombrerol(String nombrerol) {
        this.nombrerol = nombrerol;
    }
    
    // Getters y setters adicionales para la relación con Usuarios
    
    /**
     * Obtiene la lista de usuarios asociados a este rol.
     * 
     * @return la lista de usuarios asociados a este rol.
     */
    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    /**
     * Establece la lista de usuarios asociados a este rol.
     * 
     * @param usuarios la lista de usuarios asociados a este rol.
     */
    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }
}
