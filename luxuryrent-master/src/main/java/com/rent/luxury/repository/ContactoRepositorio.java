package com.rent.luxury.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.luxury.model.Contacto;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Contacto.
 */
public interface ContactoRepositorio extends JpaRepository<Contacto, Long> {

    /**
     * Busca un contacto por su ID.
     *
     * @param idContacto el ID del contacto que se desea buscar.
     * @return el contacto que coincide con el ID especificado.
     */
    Contacto findById(String idContacto);
}
