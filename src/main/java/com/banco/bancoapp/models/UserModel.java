package com.banco.bancoapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @Column(name = "nif", length = 9, nullable = false)
    private String nif;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "anyoNacimiento", length = 4)
    private String anyoNacimiento;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono", length = 9)
    private int telefono;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "userModels", cascade = CascadeType.ALL)
    private Set<AccountModel> accountModels = new HashSet<>();

}