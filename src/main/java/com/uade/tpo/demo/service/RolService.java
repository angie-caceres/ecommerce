package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.Rol;
import com.uade.tpo.demo.exceptions.*;

public interface RolService {

    public List<Rol> getRoles();

    public Optional<Rol> getRolById(Long rolId);

    public Rol createRol(String nombre) throws RecursoDuplicateException;//seria ROl
    
    
}

