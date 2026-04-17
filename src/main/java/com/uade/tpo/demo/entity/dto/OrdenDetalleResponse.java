package com.uade.tpo.demo.entity.dto;

import com.uade.tpo.demo.entity.dto.ItemOrdenResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrdenDetalleResponse {

    private Long idOrden;
    //private Long idUsuario; 
    private Date fechaVenta;
    private List<ItemOrdenResponse> items;
    private Float total;
    private String estado;
    private String metodoPago;
}
