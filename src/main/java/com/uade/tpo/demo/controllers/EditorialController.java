package com.uade.tpo.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.entity.dto.EditorialRequest;
import com.uade.tpo.demo.exceptions.RecursoDuplicateException;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.EditorialService;

@RestController
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @GetMapping
    public ResponseEntity<List<Editorial>> getEditoriales() {
        return ResponseEntity.ok(editorialService.getEditoriales());
    }
   @GetMapping("/{id}")
    public ResponseEntity<Editorial> getEditorialById(@PathVariable Long id) 
            throws RecursoNotFoundException {

        return ResponseEntity.ok(editorialService.getEditorialById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Editorial> getEditorialByNombre(@RequestParam String nombre) 
            throws RecursoNotFoundException {
        return ResponseEntity.ok(editorialService.getEditorialByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<Object> createEditorial(@RequestBody EditorialRequest request)
            throws RecursoDuplicateException {
        Editorial result = editorialService.createEditorial(request.getNombre());
        return ResponseEntity.created(URI.create("/editoriales/" + result.getId())).body(result);
    }
    @PutMapping("/{id}")
    public Editorial updateEditorial(
            @PathVariable Long id,
            @RequestBody EditorialRequest request
    ) throws RecursoNotFoundException {

        return editorialService.updateEditorial(
            id,
            request.getNombre()
        );
    }
    @DeleteMapping("/{id}")
    public void deleteEditorial(@PathVariable Long id) 
            throws RecursoNotFoundException {

        editorialService.deleteEditorial(id);
    }
}