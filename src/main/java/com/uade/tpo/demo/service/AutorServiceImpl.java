package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Autor;
import com.uade.tpo.demo.entity.dto.AutorRequest;
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

    @Override
    public void deleteAutor(Long id) throws RecursoNotFoundException {
        Autor autor = getAutorById(id);
        autorRepository.delete(autor);
    }

    @Override
    public Autor updateAutor(Long id, AutorRequest request) throws RecursoNotFoundException {
        Autor autor = getAutorById(id);
        if (request.getNombre() != null) autor.setNombre(request.getNombre());
        if (request.getApellido() != null) autor.setApellido(request.getApellido());
        if (request.getNacionalidad() != null) autor.setNacionalidad(request.getNacionalidad());
        return autorRepository.save(autor);
    }
}
