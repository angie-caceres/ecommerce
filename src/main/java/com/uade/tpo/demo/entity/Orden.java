package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Orden {

    public Orden() {
    }

    public Orden(Long idUsuario, String fechaVenta, Float total, String estado, Long idCarrito, String metodoPago) {
        this.idUsuario = idUsuario;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.idCarrito = idCarrito;
        this.metodoPago = metodoPago;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrden;

    private Long idUsuario;
    private String fechaVenta;
    private Float total;
    private String estado;
    private Long idCarrito;
    private String metodoPago;
}