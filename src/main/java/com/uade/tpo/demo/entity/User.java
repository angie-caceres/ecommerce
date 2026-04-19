package com.uade.tpo.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
//@EqualsAndHashCode(exclude = "carrito")
public class User {

    public User() {}

    public User(String username, String email, String password) {
        this.username=username;
        this.email=email;
        this.password=password;
        //this.role=role;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idUsuario;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    /*@Enumerated(EnumType.STRING)
    private Role role;*/

    @OneToOne(mappedBy = "usuario")
    private Carrito carrito;

    @OneToMany(mappedBy = "usuario")
    private List<Orden> ordenes;

    

}