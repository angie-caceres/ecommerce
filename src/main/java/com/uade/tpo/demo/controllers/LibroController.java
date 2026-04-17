package com.uade.tpo.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.dto.LibroRequest;
import com.uade.tpo.demo.entity.dto.LibroResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.LibroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    // GET /libros
    @GetMapping
    public List<LibroResponse> getLibros(
            @RequestParam(required = false) Long genero,
            @RequestParam(required = false) Float precioMin,
            @RequestParam(required = false) Float precioMax,
            @RequestParam(required = false) Long autor
    ) throws RecursoNotFoundException {

       /*  // Filtro
        /if (genero != null) {
            return libroService.getLibrosByGenero(genero);
        }

        if (precioMin != null && precioMax != null) {
            return libroService.getLibrosByPrecio(precioMin, precioMax);
        }

        if (autor != null) {
            return libroService.getLibrosByAutor(autor);
        }*/

        // Sin filtros
        
        return libroService.getLibros().stream()
            .map(libro -> libroService.convertirAResponse(libro))
            .collect(Collectors.toList());
}


    // POST /libros
    @PostMapping
    public LibroResponse createLibro(@RequestBody LibroRequest request) throws RecursoNotFoundException {
        return libroService.convertirAResponse(libroService.createLibro(request));
    }

    // GET /libros/{id}
    @GetMapping("/{id}")
    public LibroResponse getLibroById(@PathVariable Long id) {
        return libroService.convertirAResponse(libroService.getLibroById(id));
    }

    @PatchMapping("/{libroId}/descuento/{descuentoId}")
    public ResponseEntity<LibroResponse> asignarDescuento(
            @PathVariable Long libroId,
            @PathVariable Long descuentoId) {
        return ResponseEntity.ok(
            libroService.convertirAResponse(
                libroService.asignarDescuento(libroId, descuentoId)));
    }


    //actualizar stock manual

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Libro> actualizarStock(
        @PathVariable Long id,
        @RequestParam int cantidad) {
    return ResponseEntity.ok(libroService.actualizarStock(id, cantidad));
    }

}
