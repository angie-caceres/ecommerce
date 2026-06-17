package com.uade.tpo.demo.service;

import java.util.List;

import com.uade.tpo.demo.entity.Imagen;


//@Service
public interface ImagenService {
    public Imagen create(Imagen image);
    Imagen update(long id, Imagen imagen);
    public Imagen viewById(long id);
    List<Imagen> getImagenes();
    void delete(long id);

}