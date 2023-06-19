package com.rent.luxury.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rent.luxury.model.Vehiculos;
import com.rent.luxury.repository.VehiculosRepositorio;

/**
 * Clase de servicio que proporciona métodos para interactuar con la entidad Vehiculos.
 */
@Service
public class VehiculoService {
    @Autowired
    private VehiculosRepositorio vehiculoRepository;

    /**
     * Obtiene un vehículo por su ID.
     *
     * @param id el ID del vehículo que se desea obtener.
     * @return el vehículo correspondiente al ID especificado, o null si no se encuentra.
     */
    public Vehiculos obtenerVehiculoPorId(Integer id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    /**
     * Actualiza la información de un vehículo.
     *
     * @param vehiculo el vehículo con la información actualizada.
     * @throws RuntimeException si no se encuentra el vehículo con el ID especificado.
     */
    public void actualizarVehiculo(Vehiculos vehiculo) {
        Vehiculos vehiculoExistente = obtenerVehiculoPorId(vehiculo.getId());
        if (vehiculoExistente != null) {
            vehiculoExistente.setMarca(vehiculo.getMarca());
            vehiculoExistente.setModelo(vehiculo.getModelo());
            vehiculoExistente.setMatricula(vehiculo.getMatricula());
            vehiculoExistente.setKilometraje(vehiculo.getKilometraje());
            vehiculoExistente.setPreciopordia(vehiculo.getPreciopordia());
            // no se permite modificar el rol
            vehiculoRepository.save(vehiculoExistente);
        } else {
            throw new RuntimeException("No se encontró el vehículo con id " + vehiculo.getId());
        }
    }
}
