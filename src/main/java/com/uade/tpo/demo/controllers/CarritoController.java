package com.uade.tpo.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.CarritoResponse;
import com.uade.tpo.demo.entity.dto.ItemCarritoRequest;
import com.uade.tpo.demo.entity.dto.ItemCarritoResponse;
import com.uade.tpo.demo.entity.dto.OrdenDetalleResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.CarritoService;
import com.uade.tpo.demo.service.OrdenService;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired 
    private OrdenService ordenService;

    // GET /carrito/{usuarioId}
    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarritoResponse> getCarrito(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.getCarritoActivo(usuarioId));
    }

    // POST /carrito/{usuarioId}/items
    @PostMapping("/{usuarioId}/items")
    public ResponseEntity<ItemCarritoResponse> agregarItem(
        @PathVariable Long usuarioId,
        @RequestBody ItemCarritoRequest request) {
    ItemCarritoResponse nuevoItem = carritoService.agregarItem(
            usuarioId,
            request.getLibroId(),
            request.getCantidad());
    return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
}


    // PUT /carrito/{usuarioId}/items/{itemId}
    // itemId porque el service pide itemId para modificar
    @PutMapping("/{usuarioId}/items/{itemId}")
    public ResponseEntity<ItemCarritoResponse> modificarItem(
        @PathVariable Long usuarioId,
        @PathVariable Long itemId,
        @RequestBody ItemCarritoRequest request) {
    return ResponseEntity.ok(
        carritoService.modificarItem(usuarioId, itemId, request.getCantidad()));
    }

    // DELETE /carrito/{usuarioId}/items/{itemId}
    @DeleteMapping("/{usuarioId}/items/{itemId}")
    public ResponseEntity<Void> eliminarItem(
        @PathVariable Long usuarioId,
        @PathVariable Long itemId) {
    carritoService.eliminarItem(usuarioId, itemId);
    return ResponseEntity.noContent().build();
    }

    // DELETE /carrito/{usuarioId}/items (Vaciar carrito)
    @DeleteMapping("/{usuarioId}/items")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }

    // POST /carrito/{usuarioId}/checkout
    @PostMapping("/{usuarioId}/checkout")
    public ResponseEntity<OrdenDetalleResponse> checkout(@PathVariable Long usuarioId) 
            throws RecursoNotFoundException {
        Orden orden = carritoService.checkout(usuarioId);
        return ResponseEntity.ok(ordenService.getById(orden.getIdOrden()));
    }

}


