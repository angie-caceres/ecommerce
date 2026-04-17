package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class ItemOrdenResponse {
    private Long idItemOrden;
    private Long idLibro;
    private String tituloLibro;
    private int cantidad;
    private float precioUnitario;
    private float subtotal;
}
