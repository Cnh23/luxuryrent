package com.rent.luxury.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rent.luxury.model.Vehiculos;

public interface VehiculosRepositorio extends CrudRepository<Vehiculos, Integer>{
    public List<Vehiculos> findByMarca(String marca);
    public Optional<Vehiculos> findById(Integer id);
    public List<Vehiculos> findByAlquiladoFalse();
}
