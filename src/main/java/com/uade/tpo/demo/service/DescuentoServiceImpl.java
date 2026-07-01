package com.uade.tpo.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Descuento;
import com.uade.tpo.demo.exceptions.RecursoDuplicateException;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.DescuentoRepository;

@Service
public class DescuentoServiceImpl implements DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;
    
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
        if (result.isEmpty())
            throw new RecursoNotFoundException();
        Descuento descuento = result.get();
        descuento.setActivo(!descuento.isActivo()); // invierte el estado
        return descuentoRepository.save(descuento);
    }
    
}
