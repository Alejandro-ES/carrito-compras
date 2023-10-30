package com.factorit.carritocompras.service;

import com.factorit.carritocompras.TestConfig;
import com.factorit.carritocompras.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemServiceTest extends TestConfig {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void guardar_conProducto_retornaItem(){
        // PREPARACION
        Carrito carrito = carritoService.buscarPorId(1);
        Producto producto = productoService.buscarPorId(1);
        int cantidad = 1;

        // EJECUCION
        Item item = itemService.guardar(carrito, producto, cantidad);

        // COMPROBACION
        Assert.assertEquals(producto.getId(), item.getProducto().getId());
    }

    @Test
    public void guardar_conListaDeProductos_retornaListaDeItems(){
        // PREPARACION
        Carrito carrito = carritoService.buscarPorId(1);
        Map<Producto, Integer> listaProductos = new HashMap<>();
        for(Producto producto : productoService.buscar(5)){
            listaProductos.put(producto, 1);
        }

        // EJECUCION
        List<Item> listaItem = itemService.cargarProductos(carrito, listaProductos);

        // COMPROBACION
        for (Item item: listaItem) {
            Assert.assertTrue(listaProductos.containsKey(item.getProducto()));
        }
        Assert.assertEquals(listaItem.size(), listaProductos.size());
    }

    @Test
    public void buscar_conIdCarrito_retornaListaItems(){
        // EJECUCION - El carrito tiene 3 items asociados
        List<Item> listaItem = itemService.buscarPorIdCarrito(1);

        // COMPROBACION
        Assert.assertEquals(3, listaItem.size());
    }

    @Test
    public void buscar_conIdItem_retornaItem(){
        // EJECUCION
        Item itemBD = itemService.buscarPorId(1);

        // COMPROBACION
        Assert.assertEquals(1, itemBD.getId());
    }

    @Test
    public void buscar_conIdCarritoYIdProducto_retornaItem(){
        // PREPARACION
        Carrito carrito = carritoService.buscarPorId(1);
        Producto producto = carrito.getItems().get(0).getProducto();

        //EJECUCION
        Item item = itemService.buscarPorIdCarritoYPorIdProducto(carrito.getId(), producto.getId());

        // COMPROBACION
        Item itemCarrito = carrito.getItems().stream()
                .filter(elemento -> elemento.equals(item))
                .findFirst()
                .orElse(null);
        Assert.assertEquals(itemCarrito.getCarrito(), item.getCarrito());
        Assert.assertEquals(itemCarrito.getProducto(), item.getProducto());
        Assert.assertEquals(itemCarrito.getCantidad(), item.getCantidad());
    }

    @Test
    public void eliminar_conIdItem_noRetornaNada(){
        // PREPARACION
        Item itemBD = itemService.buscarPorId(1);

        // EJECUCION
        itemService.eliminar(itemBD.getId());

        // COMPROBACION
        Assert.assertNull(itemService.buscarPorId(itemBD.getId()));
    }

    @Test
    public void reducirCantidadDeProducto_conItemModificado_retornaItem(){
        // PREPARACION
        Carrito carrito = carritoService.buscarPorId(1);
        Producto producto = productoService.buscarPorId(1);
        int cantidad = 3;

        // EJECUCION
        Item itemBD = itemService.guardar(carrito, producto, cantidad);
        itemBD.setCantidad(1);
        Item itemActualizado = itemService.actualizar(itemBD);

        // COMPROBACION
        Assert.assertNotNull(itemActualizado);
        Assert.assertEquals(1, itemActualizado.getCantidad());
    }

    @Test
    public void reducirCantidadDeProductoACero_conItemModificado_retornaNull(){
        // PREPARACION
        Carrito carrito = carritoService.buscarPorId(1);
        Producto producto = productoService.buscarPorId(1);
        int cantidad = 3;

        // EJECUCION
        Item itemBD = itemService.guardar(carrito, producto, cantidad);
        itemBD.setCantidad(0);
        Item itemActualizado = itemService.actualizar(itemBD);

        // COMPROBACION
        Assert.assertNull(itemActualizado);
    }
}
