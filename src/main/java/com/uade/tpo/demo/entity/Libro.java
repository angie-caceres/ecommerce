package com.uade.tpo.demo.entity;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude = {"imagen"})
public class Libro {

    public Libro() {}



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private float precio;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private int paginas;

    @OneToMany(mappedBy = "libro")
    private List<ItemOrden> itemsOrden;

    @OneToMany(mappedBy = "libro")
    private List<ItemCarrito> itemsCarrito;

    @ManyToOne
    @JoinColumn(name = "idDescuento", nullable = false)
    private Descuento descuento;

    @ManyToOne
    @JoinColumn(name = "idGenero", nullable = false)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "idEditorial", nullable = false)
    private Editorial editorial;

    @OneToOne
    @JoinColumn(name = "idImagen")
    private Imagen imagen;

    @ManyToMany
    @JoinTable(
        name = "libro_autor",
        joinColumns = @JoinColumn(name = "idLibro"),
        inverseJoinColumns = @JoinColumn(name = "idAutor")
    )
    private List<Autor> autores;


    @ManyToOne
    @JoinColumn(name = "idAdministrador")
    private User administrador;

    @Column(nullable = false)
    private boolean activo = true;

}
