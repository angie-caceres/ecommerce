package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.UserDuplicateException;

public interface RolService {

    public List<User> getRoles();

    public Optional<User> getRolById(Long rolId);

    public User createRol(String nombre) throws UserDuplicateException;
    
    
}

