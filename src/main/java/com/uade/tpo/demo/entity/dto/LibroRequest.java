package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class LibroRequest {
    //private Long idGenero;
    //private Long idEditorial;
    private String titulo;
    private float precio;
    private int stock;
    private Long idDescuento;
    /*private Long idVendedor;
    private Long idAutor;*/
}
