package com.uade.tpo.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.Imagen;

import com.uade.tpo.demo.repository.ImagenRepository;
import com.uade.tpo.demo.repository.LibroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagenServiceImpl implements ImagenService {

    private final ImagenRepository imageRepository;
    private final LibroRepository libroRepository;

    @Override
    public Imagen create(Imagen image) {
        return imageRepository.save(image);
    }

    @Override
    public Imagen viewById(long id) {
        return imageRepository.findById(id).get();
    }

    @Override
    public Imagen update(long id, Imagen nuevaImagen) {
        Imagen imagen = imageRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Imagen con id " + id + " no encontrada"));
        imagen.setImage(nuevaImagen.getImage());
        return imageRepository.save(imagen);
    }

    @Override
    public List<Imagen> getImagenes() {
        return imageRepository.findAll();
    }

    @Override
    public void delete(long id) {
        Imagen imagen = imageRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Imagen con id " + id + " no encontrada"));

        if (libroRepository.existsByImagen(imagen)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "No se puede eliminar la imagen porque está asociada a un libro");
        }

        imageRepository.delete(imagen);
    }
}