package com.uade.tpo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.ItemOrden;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.ItemOrdenRepository;

@Service
public class ItemOrdenServiceImpl implements ItemOrdenService {
    @Autowired
    private ItemOrdenRepository itemOrdenRepository;
    //Crear item de orden
    @Override
    public ItemOrden createItemOrden(Long idOrden, Long idLibro, int cantidad, float precioUnitario) {
        ItemOrden item = new ItemOrden();
        item.setIdOrden(idOrden);
        item.setIdLibro(idLibro);
        item.setCantidad(cantidad);
        item.setPrecioUnitario(precioUnitario);
        // calcular subtotal
        item.calcularSubtotal();
        return itemOrdenRepository.save(item);
    }
    //Obtener items por orden
    @Override
    public List<ItemOrden> getItemsByOrden(Long idOrden) {
        return itemOrdenRepository.findByOrdenId(idOrden);
    }
    //Obtener total de una orden
    @Override
    public Float getTotalByOrden(Long idOrden) {
        return itemOrdenRepository.getTotalByOrden(idOrden);
    }
    //Eliminar item
    @Override
    public void deleteItemOrden(Long id) throws RecursoNotFoundException {

        ItemOrden item = itemOrdenRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException());

        itemOrdenRepository.delete(item);
    }
}