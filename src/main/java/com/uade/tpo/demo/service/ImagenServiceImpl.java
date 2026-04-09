package com.uade.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Imagen;
//import com.uade.tpo.demo.exceptions.*;
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
}