package com.uade.tpo.demo.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.exceptions.*;


public interface EditorialService {
    List<Editorial> getEditoriales();
    void deleteEditorial(Long id);
    Editorial updateEditorial(Long id, String nombre);
    Editorial getEditorialByNombre(String nombre);
    Editorial createEditorial(String nombre) throws RecursoDuplicateException;
}