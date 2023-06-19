package com.rent.luxury.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rent.luxury.model.Alquiler;
import com.rent.luxury.model.Usuarios;

public interface AlquilerRepositorio extends CrudRepository<Alquiler, Integer> {
	
	List<Alquiler> findAll(Sort sort);
	
	List<Alquiler> findByUsuarios(Usuarios usuario);
	
	@Query("SELECT a FROM Alquiler a WHERE a.vehiculo.id = :vehiculoId AND a.fechaRecogida <= :fechaFin AND a.fechaEntrega >= :fechaInicio")
	List<Alquiler> findByVehiculoIdAndFechas(@Param("vehiculoId") Integer vehiculoId, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    @Query("SELECT a FROM Alquiler a WHERE a.usuarios.id = :clienteId AND ((a.fechaRecogida BETWEEN :fechaInicio AND :fechaFin) OR (a.fechaEntrega BETWEEN :fechaInicio AND :fechaFin))")
    List<Alquiler> findByUsuariosIdAndFechas(@Param("clienteId") Integer clienteId, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
