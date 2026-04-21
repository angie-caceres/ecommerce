package com.uade.tpo.demo.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class LibroRequest {

    private String titulo;
    private String descripcion;
    private float precio;
    private int stock;
    private Long  idGenero;
    private Long  idEditorial;
    private Long idDescuento;
    private List<Long > idAutores;
}
