package com.factorit.carritocompras.service;

import com.factorit.carritocompras.domain.Carrito;
import com.factorit.carritocompras.domain.Item;
import com.factorit.carritocompras.domain.Producto;
import com.factorit.carritocompras.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item guardar(Carrito carrito, Producto producto, int cantidad){
        return itemRepository.save(new Item(carrito,producto,cantidad));
    }

    public Item actualizar(Item item){
        if(item.getCantidad() <= 0){
            this.eliminar(item.getId());
            return null;
        }else{
            return itemRepository.save(item);
        }
    }

    public List<Item> cargarProductos(Carrito carrito, Map<Producto, Integer> listaProductos) {
        List<Item> listaItemsCargados = new ArrayList<>();

        for (Map.Entry<Producto, Integer> entry : listaProductos.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidad = entry.getValue();
            Optional<Item> itemExistente = Optional.empty();

            if(carrito.getItems() != null){
                itemExistente = carrito.getItems().stream()
                    .filter(item -> item.getProducto().getId() == producto.getId())
                    .findFirst();
            }

            if (itemExistente.isPresent()) {
                Item item = itemExistente.get();
                item.setCantidad(item.getCantidad() + cantidad);
                listaItemsCargados.add(itemRepository.save(item));
            } else {
                Item newItem = new Item(carrito, producto, cantidad);
                listaItemsCargados.add(itemRepository.save(newItem));
            }
        }

        return listaItemsCargados;
    }

    public List<Item> buscarPorIdCarrito(int idCarrito){
        List<Item> listaItems = itemRepository.findByCarritoId(idCarrito);

        return listaItems;
    }

    public Item buscarPorIdCarritoYPorIdProducto(int idCarrito, int idProducto){
        return itemRepository.findByCarritoIdAndProductoId(idCarrito, idProducto).orElse(null);
    }

    public Item buscarPorId(int idItem){
        return itemRepository.findById(idItem).orElse(null);
    }

    public void eliminar(int idItem){
        itemRepository.deleteById(idItem);
    }

}
