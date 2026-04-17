package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ItemOrden {
    public ItemOrden () {}

    public ItemOrden(Orden orden, Libro libro, int cantidad, float precioUnitario, float subtotal) {
        this.orden = orden;
        this.libro = libro;
        this.cantidad = cantidad;
        this.precioUnitario =precioUnitario;
        this.subtotal = subtotal; 
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemOrden;

    @Column()
    private int cantidad;

    @Column()
    private float precioUnitario;

    @Column()
    private float subtotal;

    
    @ManyToOne
    @JoinColumn(name = "idLibro", nullable = false)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "idOrden", nullable = false)
    private Orden orden;


    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(float precioUnitario) {
        if (precioUnitario <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a 0");
        }
        this.precioUnitario = precioUnitario;
    }

    public void calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
    }
}