package com.uade.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import com.uade.tpo.demo.entity.Autor;
import com.uade.tpo.demo.entity.Genero;
import com.uade.tpo.demo.entity.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Validación
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Libro l WHERE l.titulo = ?1")
    boolean existsByTitulo(String titulo);

    /*/ Filtro por género
    @Query("SELECT l FROM Libro l WHERE l.genero = ?1")
    List<Libro> findByGenero(Genero genero);*/

    // Filtro por precio
    @Query("SELECT l FROM Libro l WHERE l.precio BETWEEN ?1 AND ?2")
    List<Libro> findByPrecioBetween(float precioMin, float precioMax);

    /*/ Filtro por autor
    @Query("SELECT l FROM Libro l WHERE l.autor = ?1")
    List<Libro> findByAutor(Autor autor);*/
}
