package com.factorit.carritocompras.repository;

import com.factorit.carritocompras.domain.Carrito;
import com.factorit.carritocompras.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    @Query("SELECT COUNT(c) FROM Carrito c WHERE c.usuario = :usuario AND EXTRACT(YEAR FROM c.fecha) = :anio AND EXTRACT(MONTH FROM c.fecha) = :mes AND c.total > 10000.0")
    Integer findByUsuarioIdAndMonthAndTotalGreaterThan(@Param("usuario") Usuario usuario, @Param("anio") int anio, @Param("mes") int mes);
}