package com.uade.tpo.demo.service;

import java.util.List;

import com.uade.tpo.demo.entity.Genero;
import com.uade.tpo.demo.exceptions.RecursoDuplicateException;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;

public interface GeneroService {
    List<Genero> getGeneros();
    Genero createGenero(String nombre) throws RecursoDuplicateException;
    Genero getGeneroById(Long id) throws RecursoNotFoundException;
    Genero updateGenero(Long id, String nombre) throws RecursoNotFoundException;
    void deleteGenero(Long id) throws RecursoNotFoundException;
}