package com.uade.tpo.demo.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.Autor;
import com.uade.tpo.demo.entity.Descuento;
import com.uade.tpo.demo.entity.Genero;
import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.entity.Descuento;
import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.entity.Genero;
import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.LibroRequest;
import com.uade.tpo.demo.entity.dto.LibroResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.AutorRepository;
import com.uade.tpo.demo.repository.DescuentoRepository;
import com.uade.tpo.demo.repository.EditorialRepository;
import com.uade.tpo.demo.repository.GeneroRepository;
import com.uade.tpo.demo.repository.ImagenRepository;
import com.uade.tpo.demo.repository.ItemCarritoRepository;
import com.uade.tpo.demo.repository.ItemOrdenRepository;
import com.uade.tpo.demo.repository.LibroRepository;
import com.uade.tpo.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.uade.tpo.demo.service.*;
import java.util.Base64;
import java.sql.Blob;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final GeneroRepository generoRepository;
    private final EditorialRepository editorialRepository;
    private final DescuentoRepository descuentoRepository;
    private final ImagenRepository imagenRepository;
    private final UserRepository userRepository;
    private final AutorRepository autorRepository;
    private final ItemCarritoRepository itemCarritoRepository;
    private final ItemOrdenRepository itemOrdenRepository;

    @Override
    public List<Libro> getLibros(String genero, String autor, String editorial, Float precioMin, Float precioMax) {

        if (genero != null) {
        Genero generoEntity = generoRepository.findByNombre(genero)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el género: " + genero));
        return libroRepository.findByGenero(generoEntity);
    }

        if (autor != null) {
        Autor autorEntity = autorRepository.findByNombreOrApellido(autor)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el autor: " + autor));
        return libroRepository.findByAutor(autorEntity);
        }

        if (editorial != null) {
            Editorial editorialEntity = editorialRepository.findByNombre(editorial)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la editorial: " + editorial));
            return libroRepository.findByEditorial(editorialEntity);
        }
        if (precioMin != null && precioMax != null) {
            if (precioMin > precioMax) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio mínimo no puede ser mayor al máximo");
            }
            List<Libro> libros = libroRepository.findByPrecioBetween(precioMin, precioMax);
            if (libros.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron libros en el rango de precio " + precioMin + " - " + precioMax);
            }
            return libros;
        }
    
        return libroRepository.findAll();
    }

    @Override
    public Libro getLibroById(Long id) {
        Optional<Libro> libroOpt = libroRepository.findById(id);
        if (libroOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El libro con id " + id + " no existe");
        }
        return libroOpt.get();
    }

    @Override
    public Libro getLibroByTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el libro: " + titulo));
    }
     
    @Override
    public Libro createLibro(LibroRequest request) throws RecursoNotFoundException {

        if (libroRepository.existsByTitulo(request.getTitulo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El libro ya existe: " + request.getTitulo());
        }

        if (request.getTitulo() == null || request.getTitulo().isBlank()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título no puede estar vacío");
        }
        if (request.getPrecio() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor a 0");
        }
        if (request.getStock() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock no puede ser negativo");
        }

        Libro libro = new Libro();
        // descuento 
        if (request.getIdDescuento() != null) {
            Descuento descuento = descuentoRepository.findById(request.getIdDescuento())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Descuento no encontrado"));
        libro.setDescuento(descuento);
        }

        Genero genero = generoRepository.findById(request.getIdGenero())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el género: " + request.getIdGenero()));

        Editorial editorial = editorialRepository.findById(request.getIdEditorial())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la editorial: " + request.getIdEditorial()));

        List<Autor> autores = request.getIdAutores().stream()
            .map(id -> autorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el autor con ese id " )))
            .collect(Collectors.toList());

        
        libro.setTitulo(request.getTitulo());
        libro.setDescripcion(request.getDescripcion());
        libro.setPrecio(request.getPrecio());
        libro.setStock(request.getStock());
        libro.setGenero(genero);
        libro.setEditorial(editorial);
        libro.setAutores(autores);
        libro.setPaginas(request.getPaginas());
       

        // obtener el admin autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User admin = userRepository.findByEmail(auth.getName()).orElseThrow();
        libro.setAdministrador(admin);

       

        return libroRepository.save(libro);
    }

    @Override
    public Libro asignarImagen(Long libroId, Long imagenId) {
        Libro libro = getLibroById(libroId);
        Imagen imagen = imagenRepository.findById(imagenId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la imagen con id: " + imagenId));
        libro.setImagen(imagen);
        return libroRepository.save(libro);
    }

    @Override
    public void deleteLibro(Long id) {
        Libro libro = getLibroById(id);
        if (!itemOrdenRepository.findByIdLibro(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "No se puede eliminar el libro porque tiene órdenes asociadas");
        }
        itemCarritoRepository.deleteByLibroId(id);
        libroRepository.delete(libro);
    }
    

    @Override
    public Libro actualizarGenero(Long id, Long idGenero) {
        Libro libro = getLibroById(id);
        Genero genero = generoRepository.findById(idGenero)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el género con id: " + idGenero));
        libro.setGenero(genero);
        return libroRepository.save(libro);
    }

    @Override
    public Libro actualizarEditorial(Long id, Long idEditorial) {
        Libro libro = getLibroById(id);
        Editorial editorial = editorialRepository.findById(idEditorial)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la editorial con id: " + idEditorial));
        libro.setEditorial(editorial);
        return libroRepository.save(libro);
    }

    @Override
    public Libro actualizarAutores(Long id, List<Long> idAutores) {
        Libro libro = getLibroById(id);
        List<Autor> autores = idAutores.stream()
            .map(idAutor -> autorRepository.findById(idAutor)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el autor con id: " + idAutor)))
            .collect(Collectors.toList());
        libro.setAutores(autores);
        return libroRepository.save(libro);
    }


    public boolean tieneStock(Long id, int cantidadSolicitada) {
        return libroRepository.findById(id)
            .map(libro -> libro.getStock() >= cantidadSolicitada) 
            .orElse(false); 
    }


    public void descontarStock(Long id, int cantidad) {
     
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

    
        if (libro.getStock() < cantidad) {
            throw new RuntimeException("No hay stock suficiente para el libro: " + libro.getTitulo());
        }

      
        int nuevoStock = libro.getStock() - cantidad;
        libro.setStock(nuevoStock);

        libroRepository.save(libro);
    }

    /*actualizacion manual de stock para el vendedor*/
    public Libro actualizarStock(Long id, int cantidad) {
    Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Libro con id " + id + " no encontrado"));

    if (cantidad <= 0)
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "La cantidad a agregar debe ser mayor a 0");

    libro.setStock(libro.getStock() + cantidad);
    return libroRepository.save(libro);
    }

   
    @Override
    public Libro actualizarDatosBasicos(Long id, LibroRequest request) {
        Libro libro = getLibroById(id);

        if (request.getTitulo() != null && !request.getTitulo().isBlank()) {
            libro.setTitulo(request.getTitulo());
        }
        if (request.getDescripcion() != null && !request.getDescripcion().isBlank()) {
            libro.setDescripcion(request.getDescripcion());
        }
        if (request.getPrecio() > 0) {
            libro.setPrecio(request.getPrecio());
        }
        if (request.getPaginas() > 0) {
            libro.setPaginas(request.getPaginas());
        }

        return libroRepository.save(libro);
    }

   @Override
    public Libro asignarDescuento(Long libroId, Long descuentoId) {
        Libro libro = getLibroById(libroId);
        Descuento descuento = descuentoRepository.findById(descuentoId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Descuento no encontrado"));
        libro.setDescuento(descuento);
        return libroRepository.save(libro);
    }

    public LibroResponse convertirAResponse(Libro libro) {
        LibroResponse response = new LibroResponse();
        response.setIdLibro(libro.getIdLibro());
        response.setTitulo(libro.getTitulo());
        response.setDescripcion(libro.getDescripcion());
        response.setPrecio(libro.getPrecio());
        response.setStock(libro.getStock());
        response.setPaginas(libro.getPaginas());

    if (libro.getGenero() != null)
        response.setGenero(libro.getGenero().getNombre());

    if (libro.getEditorial() != null)
        response.setEditorial(libro.getEditorial().getNombre());

    if (libro.getAutores() != null)
    response.setAutores(libro.getAutores().stream()
        .map(a -> a.getNombre() + " " + a.getApellido())
        .collect(Collectors.toList()));

  
    if (libro.getImagen() != null) {
    try {
        Blob blob = libro.getImagen().getImage();
        String base64 = Base64.getEncoder()
            .encodeToString(blob.getBytes(1, (int) blob.length()));
        response.setImagen(base64);
    } catch (SQLException e) {
        response.setImagen(null);
    }
    }
    
    if (libro.getDescuento() != null) {
            response.setPorcentajeDescuento(libro.getDescuento().getPorcentaje());
        }
        return response;
    }
}

