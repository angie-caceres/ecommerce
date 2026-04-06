package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.OrdenRequest;
import com.uade.tpo.demo.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl {

    @Autowired
    private OrdenRepository ordenRepository;

    public Orden createOrden(OrdenRequest request) {
        Orden orden = new Orden(
                request.getIdUsuario(),
                request.getFechaVenta(),
                request.getTotal(),
                request.getEstado(),
                request.getIdCarrito(),
                request.getMetodoPago()
        );

        return ordenRepository.save(orden);
    }
}