package com.uade.tpo.demo.entity.dto;

import com.uade.tpo.demo.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long idUsuario;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}