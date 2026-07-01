package com.uade.tpo.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Descuento;
import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.exceptions.RecursoDuplicateException;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.DescuentoRepository;
import com.uade.tpo.demo.repository.LibroRepository;

@Service
public class DescuentoServiceImpl implements DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;

    @Autowired
    private LibroRepository libroRepository;
    public Page<Descuento> getDescuentos(PageRequest pageRequest) {
        return descuentoRepository.findAll(pageRequest);
   
    }
    public Descuento createDescuento(double porcentaje) throws RecursoDuplicateException {

        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException(
                    "El porcentaje de descuento debe estar entre 0 y 100.");
        }

        List<Descuento> existentes = descuentoRepository.findByPorcentaje(porcentaje);

        if (!existentes.isEmpty()) {
            throw new RecursoDuplicateException();
        }

        Descuento nuevo = new Descuento();
        nuevo.setPorcentaje(porcentaje);
        nuevo.setActivo(true); // por defecto activo al crear

        return descuentoRepository.save(nuevo);
    }

    public Descuento toggleActivo(Long id) throws RecursoNotFoundException {
        Optional<Descuento> result = descuentoRepository.findById(id);

        if (result.isEmpty()) {
            throw new RecursoNotFoundException();
        }

        Descuento descuento = result.get();

        if (descuento.isActivo()) {
            List<Libro> librosConDescuento = libroRepository.findByDescuento(descuento);

            // Buscar el descuento de 0%
            Descuento descuentoCero =
                    descuentoRepository.findByPorcentaje(0).get(0);

            for (Libro libro : librosConDescuento) {
                libro.setDescuento(descuentoCero);
            }

            libroRepository.saveAll(librosConDescuento);
}

        descuento.setActivo(!descuento.isActivo());

        return descuentoRepository.save(descuento);
    }
    
}
