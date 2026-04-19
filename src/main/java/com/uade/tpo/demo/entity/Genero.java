package com.uade.tpo.demo.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Genero{
    public Genero() {}
    public Genero(String nombre) {
        this.nombre = nombre;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenero;

    @Column(nullable = false)
    private String nombre;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "genero")
    private List<Libro> libros;
    
}