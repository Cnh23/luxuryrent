package com.rent.luxury.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rent.luxury.model.Alquiler;
import com.rent.luxury.model.Usuarios;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Alquiler.
 */
public interface AlquilerRepositorio extends CrudRepository<Alquiler, Integer> {
	
	/**
	 * Obtiene todos los alquileres ordenados por un criterio específico.
	 * 
	 * @param sort el criterio de ordenación.
	 * @return una lista de alquileres ordenados.
	 */
	List<Alquiler> findAll(Sort sort);
	
	/**
	 * Obtiene todos los alquileres asociados a un usuario.
	 * 
	 * @param usuario el usuario.
	 * @return una lista de alquileres asociados al usuario.
	 */
	List<Alquiler> findByUsuarios(Usuarios usuario);
	
	/**
	 * Obtiene los alquileres de un vehículo que se solapan con un rango de fechas.
	 * 
	 * @param vehiculoId   el ID del vehículo.
	 * @param fechaInicio  la fecha de inicio del rango.
	 * @param fechaFin     la fecha de fin del rango.
	 * @return una lista de alquileres que se solapan con el rango de fechas especificado.
	 */
	@Query("SELECT a FROM Alquiler a WHERE a.vehiculo.id = :vehiculoId AND a.fechaRecogida <= :fechaFin AND a.fechaEntrega >= :fechaInicio")
	List<Alquiler> findByVehiculoIdAndFechas(@Param("vehiculoId") Integer vehiculoId, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    /**
     * Obtiene los alquileres de un usuario que se solapan con un rango de fechas.
     * 
     * @param clienteId    el ID del cliente.
     * @param fechaInicio  la fecha de inicio del rango.
     * @param fechaFin     la fecha de fin del rango.
     * @return una lista de alquileres que se solapan con el rango de fechas especificado.
     */
    @Query("SELECT a FROM Alquiler a WHERE a.usuarios.id = :clienteId AND ((a.fechaRecogida BETWEEN :fechaInicio AND :fechaFin) OR (a.fechaEntrega BETWEEN :fechaInicio AND :fechaFin))")
    List<Alquiler> findByUsuariosIdAndFechas(@Param("clienteId") Integer clienteId, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
