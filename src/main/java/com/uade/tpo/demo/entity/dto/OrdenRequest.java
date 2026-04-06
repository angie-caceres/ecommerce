package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class OrdenRequest {
    private Long idUsuario;
    private String fechaVenta;
    private Float total;
    private String estado;
    private Long idCarrito;
    private String metodoPago;
}