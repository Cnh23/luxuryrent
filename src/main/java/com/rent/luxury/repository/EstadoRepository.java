package com.rent.luxury.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rent.luxury.model.Estados;

@Repository
public interface EstadoRepository extends JpaRepository<Estados, Long> {
    
    Optional<Estados> findByEstado(String Estado);
    
}