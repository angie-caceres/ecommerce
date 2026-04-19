package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Autor;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.AutorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorServiceImpl implements AutorService {

    @Autowired
    private final AutorRepository autorRepository;

    @Override
    public List<Autor> getAutores() {
        return autorRepository.findAll();
    }

    @Override
    public Autor getAutorById(Long id) throws RecursoNotFoundException {
        Optional<Autor> autorOpt = autorRepository.findById(id);

        if (autorOpt.isEmpty()) {
            throw new RecursoNotFoundException();
        }

        return autorOpt.get();
    }

    @Override
    public Autor createAutor(String nombre, String apellido, String nacionalidad) {
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setApellido(apellido);
        autor.setNacionalidad(nacionalidad);

        return autorRepository.save(autor);
    }
}
