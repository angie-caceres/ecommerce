package com.uade.tpo.demo.service;

import java.util.List;

import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.dto.LibroRequest;
import com.uade.tpo.demo.entity.dto.LibroResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;

public interface LibroService {

    List<Libro> getLibros(String genero, String autor, String editorial, Float precioMin, Float precioMax);

    Libro getLibroById(Long id);

    Libro createLibro(LibroRequest request) throws RecursoNotFoundException;
    Libro actualizarStock(Long id, int cantidad);
    Libro asignarDescuento(Long libroId, Long descuentoId);
    Libro getLibroByTitulo(String titulo);
    void deleteLibro(Long id);
    Libro actualizarGenero(Long id, Long idGenero);
    Libro actualizarEditorial(Long id, Long idEditorial);
    Libro actualizarAutores(Long id, List<Long> idAutores);
    Libro asignarImagen(Long libroId, Long imagenId);

    boolean tieneStock(Long id, int cantidad);

    void descontarStock(Long id, int cantidad);
  
    LibroResponse convertirAResponse(Libro libro);

}
