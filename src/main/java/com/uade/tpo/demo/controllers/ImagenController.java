package com.uade.tpo.demo.controllers;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.exceptions.*;
import com.uade.tpo.demo.service.ImagenService;
import java.util.Base64;


@RestController
@RequestMapping("imagenes")
public class ImagenController {

    @Autowired
    private ImagenService imageService;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<ImagenResponse> displayImage(@RequestParam("id") long id) throws IOException, SQLException {
        Imagen image = imageService.viewById(id);
        String encodedString = Base64.getEncoder()
                .encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().body(ImagenResponse.builder().file(encodedString).id(id).build());
    }

    @PostMapping()
    public String addImagePost(AddFileRequest request) throws IOException, SerialException, SQLException {
        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.create(Imagen.builder().image(blob).build());
        return "created";
    }
}
