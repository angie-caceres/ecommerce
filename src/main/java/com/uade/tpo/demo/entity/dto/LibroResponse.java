package com.uade.tpo.demo.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class LibroResponse {
    private Long idLibro;
    private String titulo;
    private String descripcion;
    private float precio;
    private int paginas;
    private int stock;
    private String genero;
    private String editorial;
    private List<String> autores;
    private String imagen; // base64
    private Double porcentajeDescuento;
    private boolean activo;

    
    

}