package com.uade.tpo.demo.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


@Data
public class CarritoResponse {
    private Long idCarrito;
    private String estado;
    private float total;
    private LocalDateTime fechaUltimaActividad;
    private List<ItemCarritoResponse> items;
    
}
