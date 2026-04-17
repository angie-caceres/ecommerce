package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class LibroResponse {
    private Long idLibro;
    private String titulo;
    //private String descripcion;
    private float precio;
    private int stock;
    //private String nombreGenero;
    //private String nombreEditorial;
    //private String nombreAutor;
   // private Long idVendedor;
    private Double porcentajeDescuento;
}