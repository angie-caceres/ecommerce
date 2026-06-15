package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class ItemCarritoResponse {
    private Long idItemCarrito;
    private Long idLibro;
    private String tituloLibro;
    private String imagen;
    private int cantidad;
    private float precioUnitario;
    private float subtotal;
}
