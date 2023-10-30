package com.factorit.carritocompras.service;

import com.factorit.carritocompras.TestConfig;
import com.factorit.carritocompras.domain.Carrito;
import com.factorit.carritocompras.domain.MembresiaVip;
import com.factorit.carritocompras.domain.Producto;
import com.factorit.carritocompras.domain.Usuario;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembresiaVipServiceTest extends TestConfig{

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MembresiaVipService membresiaVipService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoService carritoService;

    @Test
    public void buscar_conIdUsuario_retornaMembresiaVip(){
        // PREPARACION
        int mes = 10;
        int anio = 2023;
        Usuario usuario = usuarioService.buscarPorId(3);

        // EJECUCION
        MembresiaVip membresiaVip = membresiaVipService.buscarUsuarioVip(anio, mes, usuario.getId());

        // COMPROBACION
        Assert.assertEquals(3, membresiaVip.getUsuario().getId());
    }

    @Test
    public void buscarClientesVip_conFechaDeterminada_retornaListaDeClientes(){
        // PREPARACION
        int mes = 10;
        int anio = 2023;

        // EJECUCION
        List<Usuario> listaUsuarios = membresiaVipService.buscarUsuariosVips(anio, mes);

        // COMPROBACION
        Assert.assertEquals(3, listaUsuarios.size());
    }

    @Test
    public void buscarClientesNoVip_conFechaDeterminada_retornaListaDeClientes(){
        // PREPARACION
        int mes = 10;
        int anio = 2023;

        // EJECUCION
        List<Usuario> listaUsuariosNoVip = membresiaVipService.buscarUsuariosNoVips(anio, mes);

        // COMPROBACION
        Assert.assertEquals(2, listaUsuariosNoVip.size());
    }

    @Test
    public void crear_cuandoUsuarioSupera10000CompradosEsteMes_retornaMembresiaVip(){
        // PREPARACION
        Usuario usuario = usuarioService.buscarPorId(4);
        LocalDate fechaActual = LocalDate.now();
        Map<Producto, Integer> listaProductos = new HashMap<>();
        listaProductos.put(productoService.buscarPorId(1),4);
        listaProductos.put(productoService.buscarPorId(2),2);
        listaProductos.put(productoService.buscarPorId(3),2);
        listaProductos.put(productoService.buscarPorId(4),3);
        Carrito carrito = carritoService.crearCarrito(listaProductos, usuario, fechaActual);

        // EJECUCION
        MembresiaVip membresiaVip = membresiaVipService.crear(usuario, carrito);

        // COMPROBACION
        Assert.assertNotNull(membresiaVip);
        Assert.assertEquals(membresiaVip.getUsuario().getId(), usuario.getId());
    }
}
