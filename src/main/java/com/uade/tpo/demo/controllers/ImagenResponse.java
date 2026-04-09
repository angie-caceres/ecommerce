package com.uade.tpo.demo.controllers;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImagenResponse {
    private Long id;
    private String file;
}
