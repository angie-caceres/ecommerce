package com.uade.tpo.demo.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.dto.OrdenDetalleResponse;
import com.uade.tpo.demo.entity.dto.OrdenResponse;
import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.ItemOrden;
import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.OrdenRepository;
import com.uade.tpo.demo.repository.UserRepository;

@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ItemOrdenService itemOrdenService;

    @Autowired
    private UserRepository userRepository;


    public Long obtenerUsuarioIdDesdeEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Usuario no encontrado"))
        .getIdUsuario();
}

    // Obtener detalle de una orden con sus items embedidos
    @Override
    public List<OrdenResponse> getByUsuario(Long idUsuario) {
    return ordenRepository.findByUsuarioIdUsuario(idUsuario).stream()
            .map(orden -> {
                OrdenResponse response = new OrdenResponse();
                response.setIdOrden(orden.getIdOrden());
                response.setIdUsuario(orden.getUsuario().getIdUsuario());
                response.setFechaVenta(orden.getFechaVenta());
                response.setTotal(orden.getTotal());
                response.setEstado(orden.getEstado());
                response.setMetodoPago(orden.getMetodoPago());
                return response;
            }).collect(Collectors.toList());
    }

    @Override
    public OrdenDetalleResponse getById(Long idOrden) {
    Orden orden = ordenRepository.findById(idOrden)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "La orden con id " + idOrden + " no existe"));

    OrdenDetalleResponse response = new OrdenDetalleResponse();
    response.setIdOrden(orden.getIdOrden());
    response.setFechaVenta(orden.getFechaVenta());
    response.setTotal(orden.getTotal());
    response.setEstado(orden.getEstado());
    response.setMetodoPago(orden.getMetodoPago());
    response.setItems(itemOrdenService.getItemsByOrden(idOrden));

    return response;
    }

    

    //todas las ordenes
    @Override
    public List<OrdenResponse> getOrdenes() {
    return ordenRepository.findAll().stream()
            .map(orden -> {
                OrdenResponse response = new OrdenResponse();
                response.setIdOrden(orden.getIdOrden());
                response.setIdUsuario(orden.getUsuario().getIdUsuario());
                response.setFechaVenta(orden.getFechaVenta());
                response.setTotal(orden.getTotal());
                response.setEstado(orden.getEstado());
                response.setMetodoPago(orden.getMetodoPago());
                return response;
            }).collect(Collectors.toList());
    }

    

    // Crear orden desde el checkout. Llamado por CarritoService
    @Override
    @Transactional
    public Orden crearDesdeCarrito(Carrito carrito, List<ItemCarrito> items, String metodoPago) {

        // 1 crear la cabecera de la orden
        Orden nuevaOrden = new Orden();
        nuevaOrden.setUsuario(carrito.getUsuario());
        nuevaOrden.setCarrito(carrito);
        nuevaOrden.setFechaVenta(new Date());
        nuevaOrden.setEstado("CONFIRMADA");
         nuevaOrden.setMetodoPago(metodoPago);

        // 2 transformar items del carrito a items de la orden
        List<ItemOrden> itemsOrden = items.stream().map(itemCarrito -> {
            ItemOrden itemOrden = new ItemOrden();
            itemOrden.setLibro(itemCarrito.getLibro());
            itemOrden.setCantidad(itemCarrito.getCantidad());
            itemOrden.setPrecioUnitario(itemCarrito.getPrecioUnitario());
            itemOrden.calcularSubtotal();
            itemOrden.setOrden(nuevaOrden);
            return itemOrden;
        }).collect(Collectors.toList());

        // 3 asignar items y calcular total
        nuevaOrden.setItems(itemsOrden);
        float total = (float) itemsOrden.stream()
                .mapToDouble(ItemOrden::getSubtotal)
                .sum();
        nuevaOrden.setTotal(total);

        // 4 guardar todo con cascade
        return ordenRepository.save(nuevaOrden);
    }

  
}