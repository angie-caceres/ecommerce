package com.uade.tpo.demo.service;


import java.util.List;

import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.exceptions.RecursoDuplicateException;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;


public interface EditorialService {
    List<Editorial> getEditoriales();
    Editorial getEditorialById(Long id) throws RecursoNotFoundException;
    Editorial getEditorialByNombre(String nombre);
    Editorial createEditorial(String nombre) throws RecursoDuplicateException;
    Editorial updateEditorial(Long id, String nombre) throws RecursoNotFoundException;
    void deleteEditorial(Long id) throws RecursoNotFoundException;
}