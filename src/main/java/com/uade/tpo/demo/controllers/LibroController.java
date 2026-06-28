package com.uade.tpo.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @Autowired
    private final LibroService libroService;

    // GET /libros/todos — solo admin, incluye activos e inactivos
    @GetMapping("/todos")
    public ResponseEntity<List<LibroResponse>> getTodosLosLibros() {
        List<LibroResponse> libros = libroService.getTodosLosLibros()
            .stream()
            .map(libroService::convertirAResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(libros);
    }

    // GET /libros
    @GetMapping
    public ResponseEntity<List<LibroResponse>> getLibros(
        @RequestParam(required = false) String genero,
        @RequestParam(required = false) String autor,
        @RequestParam(required = false) String editorial,
        @RequestParam(required = false) Float precioMin,
        @RequestParam(required = false) Float precioMax
) {
    List<LibroResponse> libros = libroService.getLibros(genero, autor, editorial, precioMin, precioMax)
        .stream()
        .map(libroService::convertirAResponse)
        .collect(Collectors.toList());
    return ResponseEntity.ok(libros);
}


    // POST /libros
    @PostMapping
    public ResponseEntity<LibroResponse> createLibro(@RequestBody LibroRequest request) throws RecursoNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(libroService.convertirAResponse(libroService.createLibro(request)));
    }

    // GET /libros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> getLibroById(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.convertirAResponse(libroService.getLibroById(id)));
    }

    // GET /libros/{id}/admin — incluye libros desactivados
    @GetMapping("/{id}/admin")
    public ResponseEntity<LibroResponse> getLibroByIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.convertirAResponse(libroService.getLibroByIdAdmin(id)));
    }

    //GET localhost:4002/libros/buscar?titulo=Cien años de soledad
    @GetMapping("/buscar")
    public ResponseEntity<LibroResponse> getLibroByTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(libroService.convertirAResponse(libroService.getLibroByTitulo(titulo)));
    }
    
    //asignardescuento
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
    public ResponseEntity<LibroResponse> actualizarStock(
        @PathVariable Long id,
        @RequestParam int cantidad) {
    return ResponseEntity.ok(libroService.convertirAResponse(libroService.actualizarStock(id, cantidad)));
    }

    @PatchMapping("/{id}/genero/{idGenero}")
    public ResponseEntity<LibroResponse> actualizarGenero(
            @PathVariable Long id,
            @PathVariable Long idGenero) {
        return ResponseEntity.ok(libroService.convertirAResponse(libroService.actualizarGenero(id, idGenero)));
    }

    @PatchMapping("/{id}/editorial/{idEditorial}")
    public ResponseEntity<LibroResponse> actualizarEditorial(
            @PathVariable Long id,
            @PathVariable Long idEditorial) {
        return ResponseEntity.ok(libroService.convertirAResponse(libroService.actualizarEditorial(id, idEditorial)));
    }

    @PatchMapping("/{id}/autores")
    public ResponseEntity<LibroResponse> actualizarAutores(
            @PathVariable Long id,
            @RequestBody List<Long> idAutores) {
        return ResponseEntity.ok(libroService.convertirAResponse(libroService.actualizarAutores(id, idAutores)));
    }

    @PatchMapping("/{libroId}/imagen/{imagenId}")
    public ResponseEntity<LibroResponse> asignarImagen(
            @PathVariable Long libroId,
            @PathVariable Long imagenId) {
        return ResponseEntity.ok(libroService.convertirAResponse(libroService.asignarImagen(libroId, imagenId)));
    }
    

    @PatchMapping("/{id}")
    public ResponseEntity<LibroResponse> actualizarDatosBasicos(
            @PathVariable Long id,
            @RequestBody LibroRequest request) {
        return ResponseEntity.ok(
            libroService.convertirAResponse(
                libroService.actualizarDatosBasicos(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<LibroResponse> activarLibro(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.convertirAResponse(libroService.activarLibro(id)));
    }

}
