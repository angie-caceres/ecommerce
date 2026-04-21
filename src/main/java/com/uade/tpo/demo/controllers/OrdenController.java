package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.OrdenDetalleResponse;
import com.uade.tpo.demo.entity.dto.OrdenRequest;
import com.uade.tpo.demo.entity.dto.OrdenResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.OrdenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<OrdenResponse>> getOrdenes() {
        return ResponseEntity.ok(ordenService.getOrdenes());
    }

    //para el ADMIN
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<OrdenResponse>> getOrdenesByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ordenService.getByUsuario(idUsuario));
    }

    //para el USER
    @GetMapping("/usuario/me")
    public ResponseEntity<List<OrdenResponse>> getOrdenesByUsuario(Authentication auth) {
        Long idUsuario = ordenService.obtenerUsuarioIdDesdeEmail(auth.getName());
        return ResponseEntity.ok(ordenService.getByUsuario(idUsuario));
    }

    //buscar orden por su id
    @GetMapping("/{id}")
    public ResponseEntity<OrdenDetalleResponse> getOrdenById(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.getById(id));
    }

}