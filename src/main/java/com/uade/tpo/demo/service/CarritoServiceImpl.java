package com.uade.tpo.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.CarritoResponse;
import com.uade.tpo.demo.entity.dto.ItemCarritoResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.CarritoRepository;
import com.uade.tpo.demo.repository.ItemCarritoRepository;
import com.uade.tpo.demo.repository.UserRepository;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private LibroService libroService;

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private UserRepository userRepository;


    public Long obtenerUsuarioIdDesdeEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Usuario no encontrado"))
        .getIdUsuario();
    }
    // uso interno del service
    private Carrito obtenerCarrito(Long usuarioId) {
        return carritoRepository.findByUsuarioIdUsuario(usuarioId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El carrito para el usuario " + usuarioId + " no existe"));
    }

    // método público que devuelve el DTO
    @Override
    public CarritoResponse getCarritoActivo(Long usuarioId) {
    Carrito carrito = obtenerCarrito(usuarioId);

    CarritoResponse response = new CarritoResponse();
    response.setIdCarrito(carrito.getIdCarrito());
    response.setEstado(carrito.getEstado());
    response.setTotal(carrito.getTotal());
    response.setFechaUltimaActividad(carrito.getFechaUltimaActividad());

    List<ItemCarritoResponse> items = carrito.getItems().stream()
            .map(item -> {
                ItemCarritoResponse itemResponse = new ItemCarritoResponse();
                itemResponse.setIdItemCarrito(item.getIdItemCarrito());
                itemResponse.setIdLibro(item.getLibro().getIdLibro());
                itemResponse.setTituloLibro(item.getLibro().getTitulo());
                itemResponse.setCantidad(item.getCantidad());
                itemResponse.setPrecioUnitario(item.getPrecioUnitario());
                itemResponse.setSubtotal(item.getSubtotal());
                return itemResponse;
            }).collect(Collectors.toList());

    response.setItems(items);
    return response;

    }

    // Agregar item al carrito
    @Override
    public ItemCarritoResponse agregarItem(Long usuarioId, Long libroId, int cantidad) {
        Carrito carrito = obtenerCarrito(usuarioId);
        Libro libro = libroService.getLibroById(libroId);

    // verificar stock
    if (libro.getStock() < cantidad)
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Stock insuficiente para el libro: " + libro.getTitulo());

    ItemCarrito item = new ItemCarrito();
    item.setCarrito(carrito);
    item.setLibro(libro);
    item.setCantidad(cantidad);
    item.setPrecioUnitario(libro.getPrecio());
    item.calcularSubtotal();


    carrito.setFechaUltimaActividad(LocalDateTime.now());
    carrito.setTotal(carrito.getTotal() + item.getSubtotal());
    carritoRepository.save(carrito);

    ItemCarrito savedItem = itemCarritoRepository.save(item);

    // convertir a DTO
    ItemCarritoResponse response = new ItemCarritoResponse();
    response.setIdItemCarrito(savedItem.getIdItemCarrito());
    response.setIdLibro(savedItem.getLibro().getIdLibro());
    response.setTituloLibro(savedItem.getLibro().getTitulo());
    response.setCantidad(savedItem.getCantidad());
    response.setPrecioUnitario(savedItem.getPrecioUnitario());
    response.setSubtotal(savedItem.getSubtotal());

    return response;
}

    // Modificar cantidad de un item
    @Override
    public ItemCarritoResponse modificarItem(Long usuarioId, Long itemId, int cantidad) {

    ItemCarrito item = itemCarritoRepository.findById(itemId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El item " + itemId + " no existe"));

    Libro libro = libroService.getLibroById(item.getLibro().getIdLibro());

    if (libro.getStock() < cantidad)
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Stock insuficiente para el libro: " + libro.getTitulo());

    item.setCantidad(cantidad);
    item.calcularSubtotal();

    Carrito carrito = obtenerCarrito(usuarioId);
    List<ItemCarrito> todosLosItems = itemCarritoRepository.findByCarritoId(carrito.getIdCarrito());
    float nuevoTotal = todosLosItems.stream()
        .map(ItemCarrito::getSubtotal)
        .reduce(0f, Float::sum);
    carrito.setTotal(nuevoTotal);
    carrito.setFechaUltimaActividad(LocalDateTime.now());
    carritoRepository.save(carrito);

    ItemCarrito savedItem = itemCarritoRepository.save(item);

    // convertir a DTO
    ItemCarritoResponse response = new ItemCarritoResponse();
    response.setIdItemCarrito(savedItem.getIdItemCarrito());
    response.setIdLibro(savedItem.getLibro().getIdLibro());
    response.setTituloLibro(savedItem.getLibro().getTitulo());
    response.setCantidad(savedItem.getCantidad());
    response.setPrecioUnitario(savedItem.getPrecioUnitario());
    response.setSubtotal(savedItem.getSubtotal());

    return response;
    }

    // Eliminar item del carrito
    @Override
    public void eliminarItem(Long usuarioId, Long itemId) {
    ItemCarrito item = itemCarritoRepository.findById(itemId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El item solicitado no existe"));
    itemCarritoRepository.delete(item);
    // recalcular total del carrito
    Carrito carrito = obtenerCarrito(usuarioId);
    List<ItemCarrito> todosLosItems = itemCarritoRepository.findByCarritoId(carrito.getIdCarrito());
    float nuevoTotal = todosLosItems.stream()
            .map(ItemCarrito::getSubtotal)
            .reduce(0f, Float::sum);
    carrito.setTotal(nuevoTotal);
    carritoRepository.save(carrito);
    }


    // Checkout: lógica principal
    @Override
    @Transactional
    public Orden checkout(Long usuarioId) {

        // 1 obtener carrito activo
        Carrito carrito = obtenerCarrito(usuarioId);

        // 2 obtener items
        List<ItemCarrito> items = itemCarritoRepository.findByCarritoId(carrito.getIdCarrito());
    

        if (items.isEmpty())
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "El carrito está vacío");
    
        // 3 verificar stock de TODOS los items antes de descontar cualquiera
    
        for (ItemCarrito item : items) {
        if (!libroService.tieneStock(item.getLibro().getIdLibro(), item.getCantidad()))
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "No hay stock insuficiente para: " + item.getLibro().getTitulo());
    }

        // 4 descontar stock y aplicar descuento
        for (ItemCarrito item : items) {
        libroService.descontarStock(item.getLibro().getIdLibro(), item.getCantidad());

        Libro libro = item.getLibro();
        float precioFinal = libro.getPrecio();
        if (libro.getDescuento() != null && libro.getDescuento().isActivo()) {
            float porcentaje = (float) libro.getDescuento().getPorcentaje();
            precioFinal = precioFinal * (1 - porcentaje / 100);
        }
        item.setPrecioUnitario(precioFinal);
        item.calcularSubtotal();
        itemCarritoRepository.save(item);
    }

        // 5 crear la orden con sus items
        Orden orden = ordenService.crearDesdeCarrito(carrito, items);

        // 6 vaciar el carrito
        vaciarCarrito(usuarioId);

        return orden;
    }

    // Vaciar items del carrito (igualmente se mantiene el carrito activo)
    @Override
    public void vaciarCarrito(Long usuarioId) {
        Carrito carrito = obtenerCarrito(usuarioId);
        itemCarritoRepository.deleteByCarritoId(carrito.getIdCarrito());
        carrito.setTotal(0);
        carritoRepository.save(carrito);
    }


}