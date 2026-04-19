package com.uade.tpo.demo.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.exceptions.*;


public interface EditorialService {
    List<Editorial> getEditoriales();
    //Optional<Editorial> getEditorialById(Long id);
    Editorial getEditorialByNombre(String nombre);
    Editorial createEditorial(String nombre) throws RecursoDuplicateException;
}