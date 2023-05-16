package com.banco.bancoapp.models;

import jakarta.persistence.*;
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

    @Column(name = "fecha_op")
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
    private UserModel user;
}
