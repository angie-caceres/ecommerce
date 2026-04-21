package com.uade.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.Imagen;

import com.uade.tpo.demo.repository.ImagenRepository;



@Service
public class ImagenServiceImpl implements ImagenService {

    @Autowired
    private ImagenRepository imageRepository;

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
}