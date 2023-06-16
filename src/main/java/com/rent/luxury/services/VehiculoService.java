package com.rent.luxury.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rent.luxury.model.Vehiculos;
import com.rent.luxury.repository.VehiculosRepositorio;

@Service
public class VehiculoService {
	@Autowired
	private VehiculosRepositorio vehiculoRepository;
	
    public Vehiculos obtenerVehiculoPorId(Integer id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

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
            throw new RuntimeException("No se encontró el usuario con id " + vehiculo.getId());
        }
    }
}
