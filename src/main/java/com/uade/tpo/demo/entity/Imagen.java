package com.uade.tpo.demo.entity;

//import javax.persistence.JoinColumns;

import jakarta.persistence.Entity;

import java.sql.Blob;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//import jakarta.persistence.ManyToOne;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Imagen {
   
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Blob image;

    @Column
    private String nombre;

    /*@ManyToOne
    JoinColumns(name = "libro_id")
    private Libro libro;*/
    
    

    

}
