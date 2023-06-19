package com.rent.luxury.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.luxury.model.Contacto;

public interface ContactoRepositorio extends JpaRepository<Contacto, Long> {
	Contacto findById(String idContacto);
}
