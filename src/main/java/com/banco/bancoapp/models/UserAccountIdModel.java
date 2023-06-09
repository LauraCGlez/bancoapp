package com.banco.bancoapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserAccountIdModel implements Serializable {

    @Column(name = "user_nif", length = 9)
    private String userNif;

    @Column(name = "cuenta_banco", length = 10)
    private int cuentaBanco;

}
