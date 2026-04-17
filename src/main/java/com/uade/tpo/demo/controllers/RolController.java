package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.Rol;
import com.uade.tpo.demo.entity.dto.RolRequest;
import com.uade.tpo.demo.exceptions.RecursoDuplicateException;
import com.uade.tpo.demo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> getRoles() {
        return ResponseEntity.ok(rolService.getRoles());
    }

    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody RolRequest request) {
        try {
            return ResponseEntity.ok(rolService.createRol(request.getNombre()));
        } catch (RecursoDuplicateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


}