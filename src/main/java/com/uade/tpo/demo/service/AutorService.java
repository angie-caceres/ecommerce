package com.uade.tpo.demo.service;

import java.util.List;

import com.uade.tpo.demo.entity.Autor;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;

public interface AutorService {

    List<Autor> getAutores();

    Autor getAutorById(Long id) throws RecursoNotFoundException;

    Autor createAutor(String nombre, String apellido, String nacionalidad);
}