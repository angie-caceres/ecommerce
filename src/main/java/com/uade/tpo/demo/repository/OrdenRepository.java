package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Query("SELECT o FROM Orden o WHERE o.usuario.idUsuario = ?1")
    List<Orden> findByUsuarioIdUsuario(Long idUsuario);
}