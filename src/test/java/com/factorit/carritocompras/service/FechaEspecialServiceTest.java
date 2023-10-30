package com.factorit.carritocompras.service;

import com.factorit.carritocompras.TestConfig;
import com.factorit.carritocompras.domain.FechaEspecial;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class FechaEspecialServiceTest extends TestConfig {

    @Autowired
    private FechaEspecialService fechaEspecialService;
    @Test
    public void buscar_conFechaCorrecta_retornaFechaEspecial(){
        // PREPARACION
        LocalDate fecha = LocalDate.parse("2023-12-24");

        // EJECUCION
        FechaEspecial fechaEspecial = fechaEspecialService.buscar(fecha);

        // COMPROBACION
        Assert.assertEquals(fechaEspecial.getFecha().getYear(), fecha.getYear());
        Assert.assertEquals(fechaEspecial.getFecha().getMonthValue(), fecha.getMonthValue());
        Assert.assertEquals(fechaEspecial.getFecha().getDayOfMonth(), fecha.getDayOfMonth());
    }
}
