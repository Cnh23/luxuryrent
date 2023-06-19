package com.rent.luxury.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rent.luxury.model.Alquiler;
import com.rent.luxury.model.Usuarios;
import com.rent.luxury.repository.AlquilerRepositorio;

/**
 * Clase de servicio que proporciona métodos para interactuar con la entidad Alquiler.
 */
@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepositorio alquilerRepository;

    /**
     * Obtiene una lista de alquileres ordenados por fecha de recogida en orden ascendente.
     *
     * @return una lista de alquileres ordenados por fecha de recogida ascendente.
     */
    public List<Alquiler> obtenerAlquileresOrdenadosPorFechaAscendente() {
        return alquilerRepository.findAll(Sort.by("fechaRecogida"));
    }

    /**
     * Obtiene una lista de alquileres ordenados por fecha de recogida en orden descendente.
     *
     * @return una lista de alquileres ordenados por fecha de recogida descendente.
     */
    public List<Alquiler> obtenerAlquileresOrdenadosPorFechaDescendente() {
        return alquilerRepository.findAll(Sort.by("fechaRecogida").descending());
    }

    /**
     * Obtiene un alquiler por su ID.
     *
     * @param id el ID del alquiler que se desea obtener.
     * @return el alquiler correspondiente al ID especificado, o null si no se encuentra.
     */
    public Alquiler obtenerAlquilerPorId(Integer id) {
        return alquilerRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene una lista de alquileres asociados a un usuario.
     *
     * @param usuario el usuario.
     * @return una lista de alquileres asociados al usuario.
     */
    public List<Alquiler> obtenerReservasPorUsuario(Usuarios usuario) {
        return alquilerRepository.findByUsuarios(usuario);
    }

    /**
     * Actualiza la información de un alquiler.
     *
     * @param alquiler el alquiler con la información actualizada.
     * @throws RuntimeException si no se encuentra el alquiler con el ID especificado.
     */
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
