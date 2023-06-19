package com.rent.luxury.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rent.luxury.model.Estados;
import com.rent.luxury.repository.EstadoRepository;

/**
 * Clase de servicio que proporciona métodos para interactuar con la entidad Estados.
 */
@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    /**
     * Constructor de EstadoService.
     *
     * @param estadoRepository el repositorio de Estados.
     */
    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    /**
     * Obtiene un estado por su nombre.
     *
     * @param nombreEstado el nombre del estado que se desea obtener.
     * @return el estado correspondiente al nombre especificado, o null si no se encuentra.
     */
    public Estados obtenerEstadoPorNombre(String nombreEstado) {
        Optional<Estados> estadoOptional = estadoRepository.findByEstado(nombreEstado);
        return estadoOptional.orElse(null);
    }
}
