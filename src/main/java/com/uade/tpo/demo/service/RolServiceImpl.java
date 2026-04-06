package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Rol;
import com.uade.tpo.demo.entity.dto.RolRequest;
import com.uade.tpo.demo.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl {

    @Autowired
    private RolRepository rolRepository;

    public Rol createRol(RolRequest request) {
        Rol rol = new Rol(request.getNombre());
        return rolRepository.save(rol);
    }
}