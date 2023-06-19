package com.rent.luxury.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rent.luxury.model.Usuarios;
import com.rent.luxury.repository.UsuariosRepositorio;

/**
 * Clase de servicio que proporciona métodos para interactuar con la entidad Usuarios.
 */
@Service
public class UsuarioService {
    @Autowired
    private UsuariosRepositorio usuarioRepository;

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id el ID del usuario que se desea obtener.
     * @return el usuario correspondiente al ID especificado, o null si no se encuentra.
     */
    public Usuarios obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Actualiza la información de un usuario.
     *
     * @param usuario el usuario con la información actualizada.
     * @throws RuntimeException si no se encuentra el usuario con el ID especificado.
     */
    public void actualizarUsuario(Usuarios usuario) {
        Usuarios usuarioExistente = obtenerUsuarioPorId(usuario.getId());
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellidos(usuario.getApellidos());
            usuarioExistente.setDni(usuario.getDni());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setEmail(usuario.getEmail());
            // no se permite modificar el rol
            usuarioRepository.save(usuarioExistente);
        } else {
            throw new RuntimeException("No se encontró el usuario con id " + usuario.getId());
        }
    }
}
