package com.factorit.carritocompras.repository;

import com.factorit.carritocompras.domain.FechaEspecial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FechaEspecialRepository extends JpaRepository<FechaEspecial, Integer> {

    Optional<FechaEspecial> findByFecha(LocalDate fecha);
}