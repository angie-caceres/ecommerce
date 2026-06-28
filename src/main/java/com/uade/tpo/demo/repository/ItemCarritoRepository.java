package com.uade.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import com.uade.tpo.demo.entity.ItemCarrito;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito,Long> {

    @Query("SELECT i FROM ItemCarrito i WHERE i.carrito.idCarrito = ?1")
    List<ItemCarrito> findByCarritoId(Long idCarrito);

    @Modifying
    @Transactional
    @Query("DELETE FROM ItemCarrito i WHERE i.carrito.idCarrito = ?1")
    void deleteByCarritoId(Long idCarrito);

    @Modifying
    @Transactional
    @Query("DELETE FROM ItemCarrito i WHERE i.libro.idLibro = ?1")
    void deleteByLibroId(Long idLibro);
}
