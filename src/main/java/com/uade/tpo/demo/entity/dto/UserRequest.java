package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class UserRequest {
    private int id;
    private String username;
    private String email;
    private String password;
   // private String role;
}