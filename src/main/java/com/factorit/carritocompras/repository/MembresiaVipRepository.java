package com.factorit.carritocompras.repository;

import com.factorit.carritocompras.domain.MembresiaVip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MembresiaVipRepository extends JpaRepository<MembresiaVip, Integer> {
    @Query("SELECT mv FROM MembresiaVip mv WHERE usuario_id = :usuario AND YEAR(mv.fecha) = :anio AND MONTH(mv.fecha) = :mes")
    Optional<MembresiaVip> findByUsuarioIdYPeriodo(@Param("usuario") int usuario, @Param("anio") int anio, @Param("mes") int mes);

    @Query("SELECT mv FROM MembresiaVip mv WHERE YEAR(mv.fecha) = :anio AND MONTH(mv.fecha) = :mes")
    List<MembresiaVip> findMembresiasByPeriodo(@Param("anio") int anio, @Param("mes") int mes);
}
