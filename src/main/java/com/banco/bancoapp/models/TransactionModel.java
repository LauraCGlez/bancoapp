package com.banco.bancoapp.models;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Transaction")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_op")
    private int codigoOp;

    @Column(name = "tipo_op")
    private TipoOpModel tipoOp;

    @Column(name = "fecha_op", length = 10)
    private LocalDate fechaOp;

    @Column(name = "cantidad")
    private double cantidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuenta_origen", referencedColumnName = "numero_cuenta")
    private AccountModel cuentaOrigen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuenta_destino", referencedColumnName = "numero_cuenta")
    private AccountModel cuentaDestino;

    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "nif")
    private UserModel titular;

    @Transient
    private String cuentaOrigenStr;

    @Transient
    private String cuentaDestinoStr;

    @Transient
    private String titularStr;

    public int getCodigoOp() {
        return codigoOp;
    }

    public void setCodigoOp(int codigoOp) {
        this.codigoOp = codigoOp;
    }

    public TipoOpModel getTipoOp() {
        return tipoOp;
    }

    public void setTipoOp(TipoOpModel tipoOp) {
        this.tipoOp = tipoOp;
    }

    public LocalDate getFechaOp() {
        return fechaOp;
    }

    public void setFechaOp(LocalDate fechaOp) {
        this.fechaOp = fechaOp;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public AccountModel getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(AccountModel cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public AccountModel getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(AccountModel cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public UserModel getTitular() {
        return titular;
    }

    public void setTitular(UserModel user) {
        this.titular = user;
    }
}
