package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class ItemOrdenRequest {
    private Long idOrden;
    private Long idLibro;
    private int cantidad;
    private float precioUnitario;
}