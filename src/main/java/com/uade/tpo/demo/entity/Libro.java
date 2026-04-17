package com.uade.tpo.demo.entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Libro {

    public Libro() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    private String titulo;

    private float precio;

    private int stock;

    @OneToMany(mappedBy = "libro")
    private List<ItemOrden> itemsOrden;

    @OneToMany(mappedBy = "libro")
    private List<ItemCarrito> itemsCarrito;

    @ManyToOne
    @JoinColumn(name = "idDescuento")
    private Descuento descuento;



    /*@ManyToOne
    @JoinColumn(name = "idGenero", nullable = false)
    private Genero genero;*/
/*
    @ManyToOne
    @JoinColumn(name = "idEditorial", nullable = false)
    private Editorial editorial;

    

    @ManyToOne
    @JoinColumn(name = "idVendedor", nullable = false)
    private User vendedor;

    @ManyToOne
    @JoinColumn(name = "idAutor", nullable = false)
    private Autor autor;
*/
}
