package com.rent.luxury.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rent.luxury.model.Estados;
import com.rent.luxury.repository.EstadoRepository;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public Estados obtenerEstadoPorNombre(String nombreEstado) {
        Optional<Estados> estadoOptional = estadoRepository.findByEstado(nombreEstado);
        return estadoOptional.orElse(null); // Devuelve null si no se encuentra el estado
    }
}


