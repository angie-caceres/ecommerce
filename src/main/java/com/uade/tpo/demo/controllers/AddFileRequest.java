package com.uade.tpo.demo.controllers;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddFileRequest {
    private String name;
    private MultipartFile file;
}
