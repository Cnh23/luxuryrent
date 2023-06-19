package com.rent.luxury.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rent.luxury.model.Estados;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Estados.
 */
@Repository
public interface EstadoRepository extends JpaRepository<Estados, Long> {

    /**
     * Busca un estado por su nombre.
     *
     * @param estado el nombre del estado que se desea buscar.
     * @return un objeto Optional que contiene el estado correspondiente al nombre especificado.
     */
    Optional<Estados> findByEstado(String estado);
}
