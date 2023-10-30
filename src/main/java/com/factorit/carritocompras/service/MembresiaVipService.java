package com.factorit.carritocompras.service;

import com.factorit.carritocompras.domain.Carrito;
import com.factorit.carritocompras.domain.MembresiaVip;
import com.factorit.carritocompras.domain.Usuario;
import com.factorit.carritocompras.repository.CarritoRepository;
import com.factorit.carritocompras.repository.MembresiaVipRepository;
import com.factorit.carritocompras.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembresiaVipService {

    @Autowired
    private MembresiaVipRepository membresiaVipRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CarritoRepository carritoRepository;

    public MembresiaVip buscarUsuarioVip(int anio, int mes, int idUsuario){
        return membresiaVipRepository.findByUsuarioIdYPeriodo(idUsuario, anio, mes).orElse(null);
    }

    public List<Usuario> buscarUsuariosVips(int anio, int mes){
        List<MembresiaVip> listaMembresias = membresiaVipRepository.findMembresiasByPeriodo(anio, mes);
        return listaMembresias.stream()
                .map(MembresiaVip::getUsuario)
                .collect(Collectors.toList());
    }

    public List<Usuario> buscarUsuariosNoVips(int anio, int mes){
        return usuarioRepository.findUsuariosSinMembresiasVipEnMes(anio, mes);
    }

    public MembresiaVip crear(Usuario usuario, Carrito carrito){
        int anio = carrito.getFecha().getYear();
        int mes = carrito.getFecha().getMonthValue();
        Integer esAptoParaVip = carritoRepository.findByUsuarioIdAndMonthAndTotalGreaterThan(usuario, anio, mes);
        if(esAptoParaVip >= 1){
            return membresiaVipRepository.save(new MembresiaVip(carrito.getFecha(), usuario));
        }else{
            return null;
        }
    }
}
