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
    private List<Alquiler> alquiler;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setNombrerol(String estado) {
        this.estado = estado;
    }
    
    public List<Alquiler> getAlquileres() {
        return alquiler;
    }

    public void setAlquileres(List<Alquiler> alquiler) {
        this.alquiler = alquiler;
    }
}
