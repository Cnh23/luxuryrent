package com.rent.luxury.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.luxury.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNombrerol(String nombrerol);
}