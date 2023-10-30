package com.factorit.carritocompras.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;
    private double total;
    @Column(name = "tipo_promocion")
    @Enumerated(EnumType.STRING)
    private TipoPromocion tipoPromocion;
    @OneToMany(mappedBy = "carrito")
    private List<Item> items;

    public Carrito(Usuario usuario, LocalDate fecha, double total, TipoPromocion tipoPromocion){
        this.usuario = usuario;
        this.fecha = fecha;
        this.total = total;
        this.tipoPromocion = tipoPromocion;
    }

}
