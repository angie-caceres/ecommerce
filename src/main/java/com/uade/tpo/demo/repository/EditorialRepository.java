package com.uade.tpo.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Long> {

    @Query("select e from Editorial e where e.nombre = ?1")
    Optional<Editorial> findByNombre(String nombre);
}