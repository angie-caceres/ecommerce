package com.uade.tpo.demo.service;

import java.util.List;

import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.dto.LibroRequest;
import com.uade.tpo.demo.entity.dto.LibroResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;

public interface LibroService {

    List<Libro> getLibros();

    Libro getLibroById(Long id);

    Libro createLibro(LibroRequest request) throws RecursoNotFoundException;
    Libro actualizarStock(Long id, int cantidad);
    Libro asignarDescuento(Long libroId, Long descuentoId);

   // List<Libro> getLibrosByGenero(Long idGenero) throws RecursoNotFoundException;

    //List<Libro> getLibrosByPrecio(float precioMin, float precioMax);

    //List<Libro> getLibrosByAutor(Long idAutor) throws RecursoNotFoundException;

    boolean tieneStock(Long id, int cantidad);

    void descontarStock(Long id, int cantidad);
    //void devolverStock(Long id, int cantidad);
    LibroResponse convertirAResponse(Libro libro);

}
