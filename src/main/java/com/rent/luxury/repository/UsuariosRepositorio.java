package com.rent.luxury.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rent.luxury.model.Usuarios;


public interface UsuariosRepositorio extends CrudRepository<Usuarios, Integer> {
    Optional<Usuarios> findByEmail(String email);
    public List<Usuarios> findByRoleNombrerol(String nombreRol);
    List<Usuarios> findByDni(String dni);
}
