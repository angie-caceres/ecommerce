package com.uade.tpo.demo.controllers;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.service.ImagenService;


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

    @PostMapping(consumes = "multipart/form-data")
    public String addImagePost(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name)
            throws IOException, SerialException, SQLException {

        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        imageService.create(Imagen.builder().image(blob).build());

        return "created";
    }

    // PATCH /imagenes/{id}
    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateImage(
            @PathVariable long id,
            @ModelAttribute AddFileRequest request)
            throws IOException, SerialException, SQLException {

        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        imageService.update(id, Imagen.builder().image(blob).build());

        return ResponseEntity.ok("updated");
    }
}
