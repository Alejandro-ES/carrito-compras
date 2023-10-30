package com.factorit.carritocompras.service;

import com.factorit.carritocompras.domain.FechaEspecial;
import com.factorit.carritocompras.repository.FechaEspecialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FechaEspecialService {

    @Autowired
    private FechaEspecialRepository fechaEspecialRepository;

    public FechaEspecial buscar(LocalDate fecha){
        return fechaEspecialRepository.findByFecha(fecha).orElse(null);
    }
}
