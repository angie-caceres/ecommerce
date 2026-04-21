package com.uade.tpo.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    
    Optional<Carrito> findByUsuarioIdUsuario(Long idUsuario);

    List<Carrito> findByEstadoAndFechaUltimaActividadBefore(String estado, LocalDateTime limite);
}
