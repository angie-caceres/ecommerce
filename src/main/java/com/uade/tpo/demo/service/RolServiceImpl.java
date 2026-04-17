package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Rol;
import com.uade.tpo.demo.entity.dto.RolRequest;
import com.uade.tpo.demo.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.uade.tpo.demo.exceptions.*;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService{

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> getRolById(Long rolId) {
        return rolRepository.findById(rolId);
    }

    @Override
    public Rol createRol(String nombre) throws RecursoDuplicateException {

        List<Rol> roles = rolRepository.findByNombre(nombre);

        if (roles.isEmpty()) {
            return rolRepository.save(new Rol(nombre));
        }

        throw new RecursoDuplicateException();
    }

}