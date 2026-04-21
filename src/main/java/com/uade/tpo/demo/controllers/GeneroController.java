package com.uade.tpo.demo.controllers;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Genero;
import com.uade.tpo.demo.entity.dto.GeneroRequest;
import com.uade.tpo.demo.exceptions.RecursoDuplicateException;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.GeneroService;

@RestController
@RequestMapping("generos")
public class GeneroController {
    @Autowired
    private GeneroService generoService;
    //Obtener todos los géneros
    @GetMapping
    public ResponseEntity<List<Genero>> getGeneros() {
        return ResponseEntity.ok(generoService.getGeneros());
    }
    //Obtener género por ID
    @GetMapping("/{id}")
    public ResponseEntity<Genero> getGeneroById(@PathVariable Long id) 
        throws RecursoNotFoundException {

    return ResponseEntity.ok(generoService.getGeneroById(id));
    }
    //Crear género
    @PostMapping
    public ResponseEntity<Object> createGenero(@RequestBody GeneroRequest request)
            throws RecursoDuplicateException {
        Genero result = generoService.createGenero(request.getNombre());
        return ResponseEntity
                .created(URI.create("/generos/" + result.getIdGenero()))
                .body(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) throws RecursoNotFoundException {
        generoService.deleteGenero(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Genero> updateGenero(@PathVariable Long id, @RequestBody GeneroRequest request) 
            throws RecursoNotFoundException {
        return ResponseEntity.ok(generoService.updateGenero(id, request.getNombre()));
    }
}