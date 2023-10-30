package com.factorit.carritocompras.service;

import com.factorit.carritocompras.domain.Producto;
import com.factorit.carritocompras.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> buscar(int cantidad){
        Pageable limit = PageRequest.of(0,cantidad);
        return productoRepository.findAll(limit).toList();
    }

    public List<Producto> buscarTodos(){
        return productoRepository.findAll();
    }

    public Producto buscarPorId(int id){
        return productoRepository.findById(id).get();
    }

}
