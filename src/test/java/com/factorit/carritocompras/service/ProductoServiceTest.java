package com.factorit.carritocompras.service;

import com.factorit.carritocompras.TestConfig;
import com.factorit.carritocompras.domain.Producto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductoServiceTest extends TestConfig {

    @Autowired
    private ProductoService productoService;

    @Test
    public void buscarTodos_sinLimite_retornaListaProductos(){
        // EJECUCION
        List<Producto> lista = productoService.buscarTodos();

        // COMPROBACION / sabemos que en la base hay 5 productos cargados
        Assert.assertEquals(5, lista.size());
    }

    @Test
    public void buscarTodos_conLimite5_retornaListaProductos(){
        // PREPARACION
        int limite = 3;

        // EJECUCION
        List<Producto> lista = productoService.buscar(limite);

        // COMPROBACION
        Assert.assertEquals(limite, lista.size());
    }

    @Test
    public void buscar_conId_retornaProducto(){
        // EJECUCION
        Producto producto = productoService.buscarPorId(1);

        // COMPROBACION
        Assert.assertEquals(1, producto.getId());
    }
}
