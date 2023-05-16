package com.banco.bancoapp.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccountModel {

    @EmbeddedId
    private UserAccountIdModel id;

    @ManyToOne
    @MapsId("userNif")
    @JoinColumn(name = "user_nif", referencedColumnName = "nif")
    private UserModel userModel;

    @ManyToOne
    @MapsId("cuentaBanco")
    @JoinColumn(name = "cuenta_banco", referencedColumnName = "numero_cuenta")
    private AccountModel accountModel;
}
