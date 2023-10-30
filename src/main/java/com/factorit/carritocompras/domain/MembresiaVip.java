package com.factorit.carritocompras.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MembresiaVip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;
    @ManyToOne
    private Usuario usuario;

    public MembresiaVip(LocalDate fecha, Usuario usuario) {
        this.fecha = fecha;
        this.usuario = usuario;
    }
}
