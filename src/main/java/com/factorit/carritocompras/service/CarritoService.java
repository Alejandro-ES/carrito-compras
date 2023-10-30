package com.factorit.carritocompras.service;

import com.factorit.carritocompras.domain.*;
import com.factorit.carritocompras.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private MembresiaVipService membresiaVipService;

    @Autowired
    private FechaEspecialService fechaEspecialService;

    public Carrito buscarPorId(int id) { return carritoRepository.findById(id).orElse(null);}

    public Carrito crearCarrito(Map<Producto, Integer> listaProductos, Usuario usuario, LocalDate fechaActual){
        TipoPromocion promo = consultarPromocion(usuario, fechaActual);
        double total = calcularTotal(listaProductos, promo);

        Carrito carrito = guardar(new Carrito(usuario, fechaActual, total, promo));
        carrito.setItems(itemService.cargarProductos(carrito, listaProductos));
        actualizarMembresiaVip(usuario, carrito);
        return carrito;
    }

    public Carrito guardar(Carrito carrito){
        return carritoRepository.save(carrito);
    }

    public Carrito actualizarCarrito(Carrito carrito, Map<Producto, Integer> listaProductos){
        itemService.cargarProductos(carrito, listaProductos);
        carrito.setItems(itemService.buscarPorIdCarrito(carrito.getId()));
        Map<Producto, Integer> listaProductosActualizada = convertirListaItemsAListaProductos(carrito.getItems());
        carrito.setTotal(calcularTotal(listaProductosActualizada, carrito.getTipoPromocion()));
        return carrito;
    }

    public void eliminar(int id){
        carritoRepository.deleteById(id);
    }

    public Carrito quitarProducto(Carrito carrito, Producto producto, int cantidad){
        Item item = itemService.buscarPorIdCarritoYPorIdProducto(carrito.getId(), producto.getId());
        item.setCantidad(item.getCantidad() - cantidad);
        itemService.actualizar(item);
        return buscarPorId(carrito.getId());
    }

    public TipoPromocion consultarPromocion(Usuario usuario, LocalDate fecha){
        int anio = fecha.getYear();
        int mes = fecha.getMonthValue();
        MembresiaVip membresiaVip = membresiaVipService.buscarUsuarioVip(anio, mes, usuario.getId());
        FechaEspecial fechaEspecial = fechaEspecialService.buscar(fecha);
        if(membresiaVip != null){
            return TipoPromocion.VIP;
        }else if(fechaEspecial != null){
            return TipoPromocion.FECHA_ESPECIAL;
        }else{
            return TipoPromocion.COMUN;
        }

    }

    private double calcularTotal(Map<Producto, Integer> listaProductos, TipoPromocion promo){
        double totalAPagar = 0;
        int cantidadProductos = obtenerCantidadDeProductos(listaProductos);
        Producto productoMasBarato = new Producto("Aux", 100000.0);

        for (Map.Entry<Producto, Integer> entry : listaProductos.entrySet()) {
            totalAPagar += (entry.getKey().getPrecio() * entry.getValue());
            if(promo == TipoPromocion.VIP && cantidadProductos > 10){
                productoMasBarato = obtenerProductoMasBarato(entry.getKey(), productoMasBarato);
            }
        }

        return aplicarDescuento(cantidadProductos, totalAPagar, promo, productoMasBarato);
    }


    private void actualizarMembresiaVip(Usuario usuario, Carrito carrito){
        membresiaVipService.crear(usuario, carrito);
    }

    private Producto obtenerProductoMasBarato(Producto productoNuevo, Producto productoMasBarato){
        if(productoMasBarato.getPrecio() - productoNuevo.getPrecio() > 0){
            return productoNuevo;
        }
        return productoMasBarato;
    }

    private int obtenerCantidadDeProductos(Map<Producto, Integer> listaProductos){
        return listaProductos.entrySet().stream()
                .map(Map.Entry::getValue)
                .reduce(Integer::sum)
                .get().intValue();
    }

    private double aplicarDescuento(int cantidadProductos, double totalAcumulado, TipoPromocion promo, Producto productoMasBarato ){
        if (cantidadProductos == 4){
            totalAcumulado *= 0.75;
        } else if (cantidadProductos > 10){
            switch (promo){
                case COMUN:
                    totalAcumulado -= 100.0;
                    break;
                case FECHA_ESPECIAL:
                    totalAcumulado -= 300.0;
                    break;
                case VIP:
                    totalAcumulado -= (productoMasBarato.getPrecio() + 500.0);
                    break;
            }
        }
        return totalAcumulado;
    }

    private Map<Producto, Integer> convertirListaItemsAListaProductos(List<Item> listaDeItems){
        Map<Producto, Integer> map = listaDeItems.stream()
                .collect(Collectors.toMap(Item::getProducto, Item::getCantidad));
        return map;
    }
}
