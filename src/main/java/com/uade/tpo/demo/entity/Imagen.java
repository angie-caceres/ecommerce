package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"libro"})
public class Imagen {
   
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Blob image;

    @Column
    private String nombre;

    @JsonIgnore
    @OneToOne(mappedBy = "imagen")
    private Libro libro;
    
    

    

}
