package com.rent.luxury.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rent.luxury.model.Alquiler;
import com.rent.luxury.model.Usuarios;
import com.rent.luxury.repository.AlquilerRepositorio;

@Service
public class AlquilerService {
    
    @Autowired
    private AlquilerRepositorio alquilerRepository;
    
    public List<Alquiler> obtenerAlquileresOrdenadosPorFechaAscendente() {
        return alquilerRepository.findAll(Sort.by("fechaRecogida"));
    }
    
    public List<Alquiler> obtenerAlquileresOrdenadosPorFechaDescendente() {
        return alquilerRepository.findAll(Sort.by("fechaRecogida").descending());
    }
	
    public Alquiler obtenerAlquilerPorId(Integer id) {
        return alquilerRepository.findById(id).orElse(null);
    }
    
    public List<Alquiler> obtenerReservasPorUsuario(Usuarios usuario) {
        return alquilerRepository.findByUsuarios(usuario);
    }

    public void actualizarAlquiler(Alquiler alquiler) {
        Alquiler alqExistente = obtenerAlquilerPorId(alquiler.getId());
        if (alqExistente != null) {
        	alqExistente.setFechaRecogida(alquiler.getFechaRecogida());
            alqExistente.setFechaEntrega(alquiler.getFechaEntrega());
            alqExistente.setUsuarios(alquiler.getUsuarios());
            alqExistente.setPrecio(alquiler.getPrecio());
            alqExistente.setVehiculo(alquiler.getVehiculo());
            // no se permite modificar el rol
            alquilerRepository.save(alqExistente);
        } else {
            throw new RuntimeException("No se encontró el alquiler con id " + alquiler.getId());
        }
    }
}
