package com.factorit.carritocompras.service;

import com.factorit.carritocompras.TestConfig;
import com.factorit.carritocompras.domain.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UsuarioServiceTest extends TestConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void buscarTodos_sinParametros_retornaListaUsuarios(){
        // EJECUCION
        List<Usuario> listaUsuarios = usuarioService.buscarTodos();

        // COMPROBACION
        Assert.assertEquals(listaUsuarios.size(), 5);
    }

    @Test
    public void buscar_conId_retornaUsuario(){
        // EJECUCION
        Usuario usuario = usuarioService.buscarPorId(1);

        // COMPROBACION
        Assert.assertEquals(1, usuario.getId());
    }
}

