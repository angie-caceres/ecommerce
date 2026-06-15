package com.uade.tpo.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import org.springframework.security.core.Authentication;

import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.CarritoResponse;
import com.uade.tpo.demo.entity.dto.ItemCarritoRequest;
import com.uade.tpo.demo.entity.dto.ItemCarritoResponse;
import com.uade.tpo.demo.entity.dto.OrdenDetalleResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.CarritoService;
import com.uade.tpo.demo.service.OrdenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired 
    private OrdenService ordenService;

    // GET /carrito
    @GetMapping
    public ResponseEntity<CarritoResponse> getCarrito(Authentication auth) {
        Long usuarioId = carritoService.obtenerUsuarioIdDesdeEmail(auth.getName());
        return ResponseEntity.ok(carritoService.getCarritoActivo(usuarioId));
    }

    // POST antes /carrito/{usuarioId}/items
    //ahora /items
    @PostMapping("/items")
   
    public ResponseEntity<ItemCarritoResponse> agregarItem(
            Authentication auth,
            @RequestBody ItemCarritoRequest request) {
        Long usuarioId = carritoService.obtenerUsuarioIdDesdeEmail(auth.getName());
        return new ResponseEntity<>(carritoService.agregarItem(usuarioId, request.getLibroId(), request.getCantidad()), HttpStatus.CREATED);
    }


    // PUT /carrito/{usuarioId}/items/{itemId}
    // itemId porque el service pide itemId para modificar
    @PutMapping("/items/{itemId}")
    public ResponseEntity<ItemCarritoResponse> modificarItem(
            Authentication auth,
            @PathVariable Long itemId,
            @RequestBody ItemCarritoRequest request) {
        Long usuarioId = carritoService.obtenerUsuarioIdDesdeEmail(auth.getName());
        return ResponseEntity.ok(carritoService.modificarItem(usuarioId, itemId, request.getCantidad()));
    }

//nuevo
    @PutMapping("/items/{itemId}/incrementar")
    public ResponseEntity<ItemCarritoResponse> incrementarItem(
            Authentication auth,
            @PathVariable Long itemId) {
        Long usuarioId = carritoService.obtenerUsuarioIdDesdeEmail(auth.getName());
        return ResponseEntity.ok(carritoService.incrementarItem(usuarioId, itemId));
    }

    @PutMapping("/items/{itemId}/decrementar")
    public ResponseEntity<ItemCarritoResponse> decrementarItem(
            Authentication auth,
            @PathVariable Long itemId) {
        Long usuarioId = carritoService.obtenerUsuarioIdDesdeEmail(auth.getName());
        ItemCarritoResponse response = carritoService.decrementarItem(usuarioId, itemId);
        if (response == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(response);
    }

    // DELETE /carrito/{usuarioId}/items/{itemId}
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> eliminarItem(
            Authentication auth,
            @PathVariable Long itemId) {
        Long usuarioId = carritoService.obtenerUsuarioIdDesdeEmail(auth.getName());
        carritoService.eliminarItem(usuarioId, itemId);
        return ResponseEntity.noContent().build();
    }


    // DELETE /carrito/{usuarioId}/items (Vaciar carrito)
    @DeleteMapping("/items")
    public ResponseEntity<Void> vaciarCarrito(Authentication auth) {
        Long usuarioId = carritoService.obtenerUsuarioIdDesdeEmail(auth.getName());
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }

    // POST /carrito/{usuarioId}/checkout
    @PostMapping("/checkout")
    public ResponseEntity<OrdenDetalleResponse> checkout(Authentication auth) throws RecursoNotFoundException {
        Long usuarioId = carritoService.obtenerUsuarioIdDesdeEmail(auth.getName());
        Orden orden = carritoService.checkout(usuarioId);
        return ResponseEntity.ok(ordenService.getById(orden.getIdOrden()));
    }

}


