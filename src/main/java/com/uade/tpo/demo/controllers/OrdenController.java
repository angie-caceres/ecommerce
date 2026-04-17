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


    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<OrdenResponse>> getOrdenesByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ordenService.getByUsuario(idUsuario));
    }

    //buscar orden por su id
    @GetMapping("/{id}")
    public ResponseEntity<OrdenDetalleResponse> getOrdenById(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.getById(id));
    }

    /*@PatchMapping("/{id}/estado")// solo para ADMIN, cambiar a CANCELADA
    public ResponseEntity<Orden> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String estado = body.get("estado");
            return ResponseEntity.ok(ordenService.cambiarEstado(id, estado));
        } catch (RecursoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }*/
}