package com.rent.luxury.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rent.luxury.model.Vehiculos;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Vehiculos.
 */
public interface VehiculosRepositorio extends CrudRepository<Vehiculos, Integer> {

    /**
     * Obtiene una lista de vehículos por su marca.
     *
     * @param marca la marca de los vehículos que se desean buscar.
     * @return una lista de vehículos con la marca especificada.
     */
    List<Vehiculos> findByMarca(String marca);

    /**
     * Busca un vehículo por su ID.
     *
     * @param id el ID del vehículo que se desea buscar.
     * @return el vehículo correspondiente al ID especificado.
     */
    Optional<Vehiculos> findById(Integer id);

    /**
     * Obtiene una lista de vehículos que no están alquilados.
     *
     * @return una lista de vehículos que no están alquilados.
     */
    List<Vehiculos> findByAlquiladoFalse();
}
