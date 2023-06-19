package com.rent.luxury.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * La clase Estados representa los diferentes estados en los que puede estar un alquiler.
 */
@Entity
@Table(name = "estados")
public class Estados {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String estado;
    
    @OneToMany(mappedBy = "estado")
    @JsonIgnore
    private List<Alquiler> alquileres;

    /**
     * Obtiene el ID del estado.
     * 
     * @return ID del estado
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del estado.
     * 
     * @param id ID del estado a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del estado.
     * 
     * @return Nombre del estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el nombre del estado.
     * 
     * @param estado Nombre del estado a establecer
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Obtiene la lista de alquileres asociados al estado.
     * 
     * @return Lista de alquileres
     */
    public List<Alquiler> getAlquileres() {
        return alquileres;
    }

    /**
     * Establece la lista de alquileres asociados al estado.
     * 
     * @param alquileres Lista de alquileres a establecer
     */
    public void setAlquileres(List<Alquiler> alquileres) {
        this.alquileres = alquileres;
    }
}
