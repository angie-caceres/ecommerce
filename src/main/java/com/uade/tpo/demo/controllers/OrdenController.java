package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.OrdenRequest;
import com.uade.tpo.demo.service.OrdenServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenServiceImpl ordenService;

    @PostMapping
    public Orden createOrden(@RequestBody OrdenRequest request) {
        return ordenService.createOrden(request);
    }
}