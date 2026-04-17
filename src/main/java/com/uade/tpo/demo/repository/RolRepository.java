package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {

    List<Rol> findByNombre(String nombre);
}