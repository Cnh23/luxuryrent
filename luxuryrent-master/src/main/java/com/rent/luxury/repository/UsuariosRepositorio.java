package com.rent.luxury.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rent.luxury.model.Usuarios;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Usuarios.
 */
public interface UsuariosRepositorio extends CrudRepository<Usuarios, Integer> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email la dirección de correo electrónico del usuario que se desea buscar.
     * @return el usuario correspondiente a la dirección de correo electrónico especificada.
     */
    Optional<Usuarios> findByEmail(String email);

    /**
     * Obtiene una lista de usuarios que tienen un rol con un nombre específico.
     *
     * @param nombreRol el nombre del rol que se desea buscar.
     * @return una lista de usuarios que tienen el rol con el nombre especificado.
     */
    List<Usuarios> findByRoleNombrerol(String nombreRol);

    /**
     * Obtiene una lista de usuarios que tienen un número de identificación (DNI) específico.
     *
     * @param dni el número de identificación (DNI) que se desea buscar.
     * @return una lista de usuarios que tienen el número de identificación (DNI) especificado.
     */
    List<Usuarios> findByDni(String dni);
}

