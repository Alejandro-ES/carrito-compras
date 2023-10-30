package com.factorit.carritocompras.service;

import com.factorit.carritocompras.TestConfig;
import com.factorit.carritocompras.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CarritoServiceTest extends TestConfig {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MembresiaVipService membresiaVipService;

    @Test
    public void buscar_conIdExistente_retornaCarrito(){
        // EJECUCION
        Carrito carrito = carritoService.buscarPorId(1);

        // COMPROBACION
        Assert.assertEquals(1, carrito.getId());
    }

    @Test
    public void buscar_conIdInexistente_retornaCarritoNulo(){
        // EJECUCION
        Carrito carrito = carritoService.buscarPorId(20);

        // COMPROBACION
        Assert.assertNull(carrito);
    }

    // revisar si tiene sentido
    @Test
    public void crear_sinProductos_retornaCarrito(){
        // PREPARACION
        LocalDate fechaActual = LocalDate.now();
        Usuario usuario = usuarioService.buscarPorId(4);
        Carrito carrito = new Carrito(usuario,fechaActual, 0.0,TipoPromocion.COMUN);

        // EJECUCION
        Carrito nuevoCarrito = carritoService.guardar(carrito);

        // COMPROBACION
        Assert.assertEquals(nuevoCarrito.getUsuario().getId(), carrito.getUsuario().getId());
        Assert.assertEquals(nuevoCarrito.getFecha(), carrito.getFecha());
        Assert.assertEquals(nuevoCarrito.getTotal(), carrito.getTotal(), 0.00);
        Assert.assertEquals(nuevoCarrito.getTipoPromocion(), carrito.getTipoPromocion());
    }

    @Test
    public void crear_conTotalMayorA10000CreaCarritoYMembresiaVip_retornaCarrito(){
        // PREPARACION
        LocalDate fechaActual = LocalDate.now();
        Usuario usuario = usuarioService.buscarPorId(4);
        Map<Producto, Integer> listaProductos = new HashMap<>();
        listaProductos.put(productoService.buscarPorId(1),4);
        listaProductos.put(productoService.buscarPorId(2),2);
        listaProductos.put(productoService.buscarPorId(3),2);
        listaProductos.put(productoService.buscarPorId(4),3);
        double total = 23900.0; // 24000 es el total, pero aplicandole -100 por ser carrito comun queda 23900

        // EJECUCION
        Carrito nuevoCarrito = carritoService.crearCarrito(listaProductos, usuario, fechaActual);
        MembresiaVip membresiaVip = membresiaVipService.buscarUsuarioVip(fechaActual.getYear(), fechaActual.getMonthValue(), usuario.getId());

        // COMPROBACION
        Assert.assertEquals(total, nuevoCarrito.getTotal(), 0.0);
        Assert.assertEquals(usuario.getId(), membresiaVip.getUsuario().getId());
    }

    @Test
    public void crear_conListaDe11ProductosYPromocionComun_retornaCarrito(){
        // PREPARACION
        LocalDate fechaActual = LocalDate.now();
        Usuario usuario = usuarioService.buscarPorId(4);
        Map<Producto, Integer> listaProductos = new HashMap<>();
        listaProductos.put(productoService.buscarPorId(1),4);
        listaProductos.put(productoService.buscarPorId(2),2);
        listaProductos.put(productoService.buscarPorId(3),2);
        listaProductos.put(productoService.buscarPorId(4),3);
        double total = 23900.0; // 24000 es el total, pero aplicandole -100 por ser carrito comun queda 23900

        // EJECUCION
        Carrito nuevoCarrito = carritoService.crearCarrito(listaProductos, usuario, fechaActual);

        // COMPROBACION
        Assert.assertEquals(total, nuevoCarrito.getTotal(), 0.0);
    }

    @Test
    public void crear_conListaDe11ProductosYPromocionFechaEspecial_retornaCarrito(){
        // PREPARACION
        LocalDate fechaActual = LocalDate.parse("2023-12-24");
        Usuario usuario = usuarioService.buscarPorId(4);
        Map<Producto, Integer> listaProductos = new HashMap<>();
        listaProductos.put(productoService.buscarPorId(1),4);
        listaProductos.put(productoService.buscarPorId(2),2);
        listaProductos.put(productoService.buscarPorId(3),2);
        listaProductos.put(productoService.buscarPorId(4),3);
        double total = 23700.0; // 24000 es el total, pero aplicandole -300 por ser carrito comun queda 23700

        // EJECUCION
        Carrito nuevoCarrito = carritoService.crearCarrito(listaProductos, usuario, fechaActual);

        // COMPROBACION
        Assert.assertEquals(total, nuevoCarrito.getTotal(), 0.0);
    }

    @Test
    public void crear_conListaDe11ProductosYPromocionVip_retornaCarrito(){
        // PREPARACION
        LocalDate fechaActual = LocalDate.now();
        Usuario usuario = usuarioService.buscarPorId(1);
        Map<Producto, Integer> listaProductos = new HashMap<>();
        listaProductos.put(productoService.buscarPorId(1),4);
        listaProductos.put(productoService.buscarPorId(2),2);
        listaProductos.put(productoService.buscarPorId(3),2);
        listaProductos.put(productoService.buscarPorId(4),3);
        double total = 22000.0; // 24000 es el total, pero aplicandole -500 y binificando el mas barato por ser carrito VIP

        // EJECUCION
        Carrito nuevoCarrito = carritoService.crearCarrito(listaProductos, usuario, fechaActual);

        // COMPROBACION
        Assert.assertEquals(total, nuevoCarrito.getTotal(), 0.0);
    }

    @Test
    public void agregarProductos_conListaProductos_retornaCarrito(){
        // PREPARACION
        Carrito carrito = carritoService.buscarPorId(1);

        Map<Producto, Integer> listaProductos = new HashMap<>();
        listaProductos.put(productoService.buscarPorId(1),1);
        listaProductos.put(productoService.buscarPorId(2),2);
        listaProductos.put(productoService.buscarPorId(3),1);
        listaProductos.put(productoService.buscarPorId(4),1);
        double total = 17500;
        // EJECUCION
        Carrito nuevoCarrito = carritoService.actualizarCarrito(carrito, listaProductos);

        // COMPROBACION
        Assert.assertEquals(total, nuevoCarrito.getTotal(), 0.0);
    }

    @Test
    public void agregarProductos_conListaProducto_retornaCarritoConDescuentoAplicado(){
        // PREPARACION
        Carrito carrito = carritoService.buscarPorId(1);
        Map<Producto, Integer> listaProductos = new HashMap<>();
        double total = 5250.0; // el carrito tiene 4 productos que suman un total de 7000, si le sacamos el 25% nos queda 5250

        // EJECUCION
        Carrito nuevoCarrito = carritoService.actualizarCarrito(carrito, listaProductos);

        // COMPROBACION
        Assert.assertEquals(total, nuevoCarrito.getTotal(), 0.0);
    }

    @Test
    public void descontarCantidadDeUnProducto_conCantidadEspecifica_retornaCarritoActualizado(){
        // PREPARACION
        LocalDate fechaActual = LocalDate.now();
        Usuario usuario = usuarioService.buscarPorId(1);
        Producto producto1 = productoService.buscarPorId(1);
        Map<Producto, Integer> listaProductos = new HashMap<>();
        listaProductos.put(producto1,5);
        listaProductos.put(productoService.buscarPorId(2),2);
        listaProductos.put(productoService.buscarPorId(3),1);
        listaProductos.put(productoService.buscarPorId(4),1);
        Carrito carrito = carritoService.crearCarrito(listaProductos, usuario, fechaActual);

        // EJECUCION
        Carrito carritoActualizado = carritoService.quitarProducto(carrito, producto1, 2);

        // COMPROBACION
        int cantidadObtenida = carritoActualizado.getItems().stream()
                .filter(elemento -> elemento.getProducto().equals(producto1))
                .mapToInt(Item::getCantidad)
                .findFirst()
                .orElse(0);
        Assert.assertEquals(3, cantidadObtenida);
    }

    @Test
    public void eliminar_conIdCarrito_noRetornaNada(){
        // PREPARACION
        Carrito carrito = carritoService.buscarPorId(1);

        // EJECUCION
        carritoService.eliminar(carrito.getId());

        // COMPROBACION
        Assert.assertNull(carritoService.buscarPorId(1));
    }

}