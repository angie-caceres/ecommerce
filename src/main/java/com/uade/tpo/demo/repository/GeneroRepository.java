package com.uade.tpo.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.tpo.demo.entity.Genero;
import java.util.List;
import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    @Query("SELECT g FROM Genero g WHERE g.nombre = ?1")
    Optional<Genero> findByNombre(String nombre);
}