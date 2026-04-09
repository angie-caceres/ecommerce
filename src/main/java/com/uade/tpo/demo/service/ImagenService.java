package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Imagen;


//@Service
public interface ImagenService {
    public Imagen create(Imagen image);

    public Imagen viewById(long id);

}