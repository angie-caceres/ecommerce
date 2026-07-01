package com.uade.tpo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Autor;
import com.uade.tpo.demo.entity.Descuento;
import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.entity.Genero;
import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.entity.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByDescuento(Descuento descuento);

    // Validación (solo libros activos)
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Libro l WHERE l.titulo = ?1 AND l.activo = true")
    boolean existsByTitulo(String titulo);

    List<Libro> findAllByActivoTrue();

    //Filtro por género
    @Query("SELECT l FROM Libro l WHERE l.genero = ?1 AND l.activo = true")
    List<Libro> findByGenero(Genero genero);

    // Filtro por precio
    @Query("SELECT l FROM Libro l WHERE l.precio BETWEEN ?1 AND ?2 AND l.activo = true")
    List<Libro> findByPrecioBetween(float precioMin, float precioMax);

    // Filtro por autor
    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE a = ?1 AND l.activo = true")
    List<Libro> findByAutor(Autor autor);

    Optional<Libro> findByTituloAndActivoTrue(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.editorial = ?1 AND l.activo = true")
    List<Libro> findByEditorial(Editorial editorial);

    boolean existsByImagen(Imagen imagen);

}
