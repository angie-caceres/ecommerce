package com.uade.tpo.demo.service;

import java.util.List;
import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.OrdenDetalleResponse;
import com.uade.tpo.demo.entity.dto.OrdenResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;

public interface OrdenService {
    OrdenDetalleResponse getById(Long idOrden);
    List<OrdenResponse> getByUsuario(Long idUsuario);
    List<OrdenResponse> getOrdenes();
   
    Orden crearDesdeCarrito(Carrito carrito, List<ItemCarrito> items, String metodoPago);
    public Long obtenerUsuarioIdDesdeEmail(String email);

}