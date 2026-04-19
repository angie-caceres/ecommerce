package com.uade.tpo.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    // POST /autores
    @PostMapping
    public Autor createAutor(@RequestBody AutorRequest request) {
        return autorService.createAutor(
                request.getNombre(),
                request.getApellido(),
                request.getNacionalidad()
        );
    }
}