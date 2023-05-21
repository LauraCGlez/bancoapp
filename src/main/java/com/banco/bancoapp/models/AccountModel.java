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
    @Column(name = "numero_cuenta", nullable = false, length = 10)
    private int numeroCuenta;

    @Column(name = "saldo", nullable = false)
    private double saldo = 0;

    @Column(name = "fecha_creacion", length = 10)
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
        if (saldo > cantidad){
            saldo -= cantidad;
            posible = true;
        } else {
            posible = false;
        }
        return posible;
    }

    public void ingresarDinero(double cantidad){

        saldo += cantidad;
    }

    //TODO GENERATE GETTER AND SETTER

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Set<TransactionModel> getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Set<TransactionModel> cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(Set<UserModel> userModels) {
        this.userModels = userModels;
    }

    public Set<TransactionModel> getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Set<TransactionModel> cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


}
