package com.uade.tpo.demo.controllers;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Descuento;
import com.uade.tpo.demo.entity.dto.DescuentoRequest;
import com.uade.tpo.demo.exceptions.*;
import com.uade.tpo.demo.service.DescuentoService;
import org.springframework.data.domain.Page;



@RestController
@RequestMapping("descuentos")
public class DescuentoController {
    @Autowired
    private DescuentoService descuentoService;

    @GetMapping
    public ResponseEntity<Page<Descuento>> getDescuentos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(descuentoService.getDescuentos(PageRequest.of(page, size)));
    }

    @PatchMapping("/{id}/toggle")  // PATCH porque es una modificación parcial, se desactiva el Desc
    public ResponseEntity<Descuento> toggleActivo(@PathVariable Long id)
            throws RecursoNotFoundException {
        return ResponseEntity.ok(descuentoService.toggleActivo(id));
    }

    @PostMapping
    public ResponseEntity<Object> createDescuento(@RequestBody DescuentoRequest request)
            throws RecursoDuplicateException {
        Descuento result = descuentoService.createDescuento(request.getPorcentaje());
        return ResponseEntity.created(URI.create("/descuentos/" + result.getId())).body(result);
    }


}
