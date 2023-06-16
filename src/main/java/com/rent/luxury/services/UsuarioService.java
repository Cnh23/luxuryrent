package com.rent.luxury.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rent.luxury.model.Usuarios;
import com.rent.luxury.repository.UsuariosRepositorio;
@Service
public class UsuarioService {
	@Autowired
	private UsuariosRepositorio usuarioRepository;
	
    public Usuarios obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

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
