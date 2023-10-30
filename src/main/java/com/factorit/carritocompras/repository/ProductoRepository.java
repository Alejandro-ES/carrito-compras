package com.factorit.carritocompras.repository;

import com.factorit.carritocompras.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
