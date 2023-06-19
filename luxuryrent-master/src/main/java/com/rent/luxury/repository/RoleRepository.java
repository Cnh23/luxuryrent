package com.rent.luxury.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.luxury.model.Role;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Role.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Busca un rol por su nombre.
     *
     * @param nombrerol el nombre del rol que se desea buscar.
     * @return el rol correspondiente al nombre especificado.
     */
    Role findByNombrerol(String nombrerol);
}
