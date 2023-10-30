package com.factorit.carritocompras.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    private String usuario;
    private String contrasenia;

    public Usuario(String nombreCompleto, String usuario, String contraseña) {
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.contrasenia = contraseña;
    }
}
