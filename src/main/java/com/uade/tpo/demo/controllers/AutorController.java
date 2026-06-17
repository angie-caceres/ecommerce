package com.uade.tpo.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Autor;
import com.uade.tpo.demo.entity.dto.AutorRequest;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.AutorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    @Autowired
    private final AutorService autorService;

    // GET /autores
    @GetMapping
    public List<Autor> getAutores() {
        return autorService.getAutores();
    }

    // GET /autores/{id}
    @GetMapping("/{id}")
    public Autor getAutorById(@PathVariable Long id) throws RecursoNotFoundException {
        return autorService.getAutorById(id);
    }

    // DELETE /autores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) throws RecursoNotFoundException {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /autores/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Autor> updateAutor(@PathVariable Long id, @RequestBody AutorRequest request) throws RecursoNotFoundException {
        return ResponseEntity.ok(autorService.updateAutor(id, request));
    }

    // POST /autores
    @PostMapping
        public Autor createAutor(@RequestBody AutorRequest request, Authentication authentication) {
            System.out.println("USUARIO: " + authentication.getName());
            System.out.println("ROLES: " + authentication.getAuthorities());

            return autorService.createAutor(
                    request.getNombre(),
                    request.getApellido(),
                    request.getNacionalidad()
            );
    }
}