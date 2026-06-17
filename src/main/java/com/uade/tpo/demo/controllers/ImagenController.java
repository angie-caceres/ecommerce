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
    @GetMapping("/todas") // Le ponemos una ruta clara para no chocar
    public ResponseEntity<java.util.List<ImagenResponse>> getAllImages() throws IOException, SQLException {
        java.util.List<Imagen> listaImagenes = imageService.getImagenes(); // Asegurate de que tu Service tenga un método para listar todo
        java.util.List<ImagenResponse> respuesta = new java.util.ArrayList<>();
        
        if (listaImagenes == null) {
            return ResponseEntity.ok().body(respuesta);
        }

        for (Imagen img : listaImagenes) {
            // 🌟 VALIDACIÓN SALVAVIDAS: Verificamos que el registro no sea nulo y que la propiedad image (el Blob) exista
            if (img != null && img.getImage() != null) {
                try {
                    // Solo si pasa el filtro calculamos el length y convertimos a Base64
                    String encodedString = Base64.getEncoder()
                            .encodeToString(img.getImage().getBytes(1, (int) img.getImage().length()));
                    
                    respuesta.add(ImagenResponse.builder()
                            .id(img.getId())
                            .file(encodedString)
                            .name(img.getNombre() != null ? img.getNombre() : "Imagen sin nombre")
                            .build());
                } catch (Exception e) {
                    System.out.println("Se ignoró un registro corrupto con ID: " + img.getId());
                }
            }
        }
        return ResponseEntity.ok().body(respuesta);
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<ImagenResponse> displayImage(@RequestParam("id") long id) throws IOException, SQLException {
        Imagen image = imageService.viewById(id);
        String encodedString = Base64.getEncoder()
                .encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().body(ImagenResponse.builder().file(encodedString).id(id).name(image.getNombre()).build());
    }

    @PostMapping()
    public String addImagePost(AddFileRequest request) throws IOException, SerialException, SQLException {
        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.create(Imagen.builder().nombre(request.getName()).image(blob).build());
        return "created";
    }

    // PATCH /imagenes/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateImage(@PathVariable long id, AddFileRequest request) 
            throws IOException, SerialException, SQLException {
        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.update(id, Imagen.builder().nombre(request.getName()).image(blob).build());
        return ResponseEntity.ok("updated");
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable long id) {
        imageService.delete(id);
        return ResponseEntity.noContent().build();
        //return ResponseEntity.ok("deleted");
    }
}
