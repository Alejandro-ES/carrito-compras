package com.factorit.carritocompras.repository;

import com.factorit.carritocompras.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u " +
            "WHERE u.id NOT IN (" +
            "    SELECT DISTINCT mv.usuario.id FROM MembresiaVip mv " +
            "    WHERE YEAR(mv.fecha) = :anno " +
            "    AND MONTH(mv.fecha) = :mes)")
    List<Usuario> findUsuariosSinMembresiasVipEnMes(int anno, int mes);
}
