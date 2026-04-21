package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;


@Data
@Entity
public class Orden {

    public Orden() {
    }

    public Orden(User usuario, Date fechaVenta, Float total, String estado, Carrito carrito, String metodoPago) {
        this.usuario = usuario;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.carrito = carrito;
        this.metodoPago = metodoPago;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrden;

    @Column
    private Date fechaVenta;

    @Column
    private Float total;

    @Column
    private String estado;
 
    @Column
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false) 
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "idCarrito")
    private Carrito carrito;
 
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<ItemOrden> items;
    
}