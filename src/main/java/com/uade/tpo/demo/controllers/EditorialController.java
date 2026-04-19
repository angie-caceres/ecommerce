package com.uade.tpo.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.entity.dto.EditorialRequest;
import com.uade.tpo.demo.exceptions.*;
import com.uade.tpo.demo.service.EditorialService;

@RestController
@RequestMapping("editoriales")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @GetMapping
    public ResponseEntity<List<Editorial>> getEditoriales() {
        return ResponseEntity.ok(editorialService.getEditoriales());
    }
   /*@GetMapping("/{id}")
    public ResponseEntity<Editorial> getEditorialById(@PathVariable Long id) {
        Optional<Editorial> result = editorialService.getEditorialById(id);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }*/

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

    
}