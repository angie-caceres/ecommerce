package com.uade.tpo.demo.service;

import java.util.List;

import com.uade.tpo.demo.entity.ItemOrden;
import com.uade.tpo.demo.entity.dto.ItemOrdenResponse;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;

public interface ItemOrdenService {

    ItemOrden createItemOrden(Long idOrden, Long idLibro, int cantidad, float precioUnitario);

    List<ItemOrdenResponse> getItemsByOrden(Long idOrden);

}