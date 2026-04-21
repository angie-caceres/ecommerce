package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.exceptions.*;
import com.uade.tpo.demo.repository.EditorialRepository;

@Service
public class EditorialServiceImpl implements EditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    public List<Editorial> getEditoriales() {
        return editorialRepository.findAll();
    }

    @Override
    public Editorial getEditorialByNombre(String nombre) {
        return editorialRepository.findByNombre(nombre)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la editorial: " + nombre));
    }

    public Editorial createEditorial(String nombre) throws RecursoDuplicateException {
        Optional<Editorial> existentes = editorialRepository.findByNombre(nombre);
        if (!existentes.isEmpty())
            throw new RecursoDuplicateException();
        return editorialRepository.save(new Editorial(nombre));
    }

    public void deleteEditorial(Long id) {
    Editorial editorial = editorialRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "No existe la editorial con id: " + id));
    editorialRepository.delete(editorial);
}

    public Editorial updateEditorial(Long id, String nombre) {
        Editorial editorial = editorialRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existe la editorial con id: " + id));
        if (nombre != null) editorial.setNombre(nombre);
        return editorialRepository.save(editorial);
    }

}