package com.uade.tpo.demo.service;

import org.springframework.http.ResponseEntity;

import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.CarritoResponse;
import com.uade.tpo.demo.entity.dto.ItemCarritoResponse;

public interface CarritoService {
    public CarritoResponse getCarritoActivo(Long usuarioId);
    public ItemCarritoResponse agregarItem(Long usuarioId, Long libroId, int cantidad);
    public ItemCarritoResponse modificarItem(Long usuarioId, Long itemId, int cantidad);
    public Long obtenerUsuarioIdDesdeEmail(String email);
    public Orden checkout(Long usuarioId);
    ItemCarritoResponse incrementarItem(Long usuarioId, Long itemId);
    ItemCarritoResponse decrementarItem(Long usuarioId, Long itemId);
    void eliminarItem(Long usuarioId, Long itemId);
    void vaciarCarrito(Long usuarioId);
    
}
