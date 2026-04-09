package com.uade.tpo.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.ItemOrden;
import com.uade.tpo.demo.entity.dto.ItemOrdenRequest;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.ItemOrdenService;

@RestController
@RequestMapping("items-orden")
public class ItemOrdenController {
    @Autowired
    private ItemOrdenService itemOrdenService;
    //Crear item de orden
    @PostMapping
    public ResponseEntity<Object> createItemOrden(@RequestBody ItemOrdenRequest request) {

        ItemOrden result = itemOrdenService.createItemOrden(
                request.getIdOrden(),
                request.getIdLibro(),
                request.getCantidad(),
                request.getPrecioUnitario()
        );
        return ResponseEntity
                .created(URI.create("/items-orden/" + result.getIdItemOrden()))
                .body(result);
    }
    //Obtener items por orden
    @GetMapping("/orden/{idOrden}")
    public ResponseEntity<List<ItemOrden>> getItemsByOrden(@PathVariable Long idOrden) {
        return ResponseEntity.ok(itemOrdenService.getItemsByOrden(idOrden));
    }
    //Obtener total de una orden
    @GetMapping("/orden/{idOrden}/total")
    public ResponseEntity<Float> getTotalByOrden(@PathVariable Long idOrden) {
        return ResponseEntity.ok(itemOrdenService.getTotalByOrden(idOrden));
    }
    //Eliminar item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemOrden(@PathVariable Long id)
            throws RecursoNotFoundException {
        itemOrdenService.deleteItemOrden(id);
        return ResponseEntity.noContent().build();
    }
}