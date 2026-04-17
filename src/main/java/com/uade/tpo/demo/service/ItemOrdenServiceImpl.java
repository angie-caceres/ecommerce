package com.uade.tpo.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.ItemOrden;
import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.ItemOrdenResponse;
import com.uade.tpo.demo.repository.ItemOrdenRepository;
import com.uade.tpo.demo.repository.OrdenRepository;

@Service
public class ItemOrdenServiceImpl implements ItemOrdenService {

    @Autowired
    private ItemOrdenRepository itemOrdenRepository;

    @Autowired
    private LibroService libroService;

    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public ItemOrden createItemOrden(Long idOrden, Long idLibro, int cantidad, float precioUnitario) {
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));
        Libro libro = libroService.getLibroById(idLibro);

        ItemOrden item = new ItemOrden();
        item.setOrden(orden);
        item.setLibro(libro);
        item.setCantidad(cantidad);
        item.setPrecioUnitario(precioUnitario);
        item.calcularSubtotal();
        return itemOrdenRepository.save(item);
    }

    @Override
    public List<ItemOrdenResponse> getItemsByOrden(Long idOrden) {
    return itemOrdenRepository.findByOrdenId(idOrden).stream()
            .map(item -> {
                ItemOrdenResponse response = new ItemOrdenResponse();
                response.setIdItemOrden(item.getIdItemOrden());
                response.setIdLibro(item.getLibro().getIdLibro());
                response.setTituloLibro(item.getLibro().getTitulo());
                response.setCantidad(item.getCantidad());
                response.setPrecioUnitario(item.getPrecioUnitario());
                response.setSubtotal(item.getSubtotal());
                return response;
            }).collect(Collectors.toList());
}
}
