package com.uade.tpo.demo.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.demo.entity.Genero;
import com.uade.tpo.demo.exceptions.RecursoDuplicateException;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.GeneroRepository;

@Service
public class GeneroServiceImpl implements GeneroService {
    @Autowired
    private GeneroRepository generoRepository;
    //Obtener todos los géneros
    @Override
    public List<Genero> getGeneros() {
        return generoRepository.findAll();
    }
    //Crear género
    @Override
    public Genero createGenero(String nombre) throws RecursoDuplicateException {
        //Validar duplicado
        if (!generoRepository.findByNombre(nombre).isEmpty()) {
            throw new RecursoDuplicateException();
        }
        Genero genero = new Genero();
        genero.setNombre(nombre);
        return generoRepository.save(genero);
    }
    //Obtener género por ID
    @Override
    public Genero getGeneroById(Long id) throws RecursoNotFoundException {
        return generoRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException());
    }
    //Eliminar género
    @Override
    public void deleteGenero(Long id) throws RecursoNotFoundException {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException());
        generoRepository.delete(genero);
    }
}