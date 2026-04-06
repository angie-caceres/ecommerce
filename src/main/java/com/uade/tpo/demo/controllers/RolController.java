package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.Rol;
import com.uade.tpo.demo.entity.dto.RolRequest;
import com.uade.tpo.demo.service.RolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolServiceImpl rolService;

    @PostMapping
    public Rol createRol(@RequestBody RolRequest request) {
        return rolService.createRol(request);
    }
}