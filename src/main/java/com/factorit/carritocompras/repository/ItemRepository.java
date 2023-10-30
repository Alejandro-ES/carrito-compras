package com.factorit.carritocompras.repository;

import com.factorit.carritocompras.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByCarritoId(int id);

    Optional<Item> findByCarritoIdAndProductoId(int idCarrito, int idProducto);
}
