package com.banco.bancoapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {

    @Id
    @Column(name = "numero_cuenta", nullable = false)
    private int numeroCuenta;

    @Column(name = "saldo", nullable = false)
    private double saldo = 0;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_account",
            joinColumns = @JoinColumn(name = "cuenta_banco", referencedColumnName = "numero_cuenta"),
            inverseJoinColumns = @JoinColumn(name = "user_nif", referencedColumnName = "nif"))
    private Set<UserModel> userModels = new HashSet<>();

    @OneToMany(mappedBy = "cuentaOrigen", fetch = FetchType.EAGER)
    private Set<TransactionModel> cuentaOrigen = new HashSet<>();

    @OneToMany(mappedBy = "cuentaDestino", fetch = FetchType.EAGER)
    private Set<TransactionModel> cuentaDestino = new HashSet<>();

    //TODO QUITAR ESTO DE AQUI
    public boolean retirarDinero(double cantidad){
        boolean posible = false;
        if (saldo > 0 && saldo > cantidad){
            saldo -= cantidad;
        }
        return posible;
    }

    public void ingresarDinero(double cantidad){

        saldo += cantidad;
    }
}
