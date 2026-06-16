package com.uade.tpo.demo.entity.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;


@Data
public class OrdenResponse {
    private Long idOrden;
    private Long idUsuario;
    private Date fechaVenta;
    private Float total;
    private String estado;
    private String metodoPago;
    private List<ItemOrdenResponse> items;
}
