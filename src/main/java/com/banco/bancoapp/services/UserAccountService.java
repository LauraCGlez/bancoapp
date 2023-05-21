package com.banco.bancoapp.services;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.UserAccountIdModel;
import com.banco.bancoapp.models.UserAccountModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.AccountRepo;
import com.banco.bancoapp.repositories.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepo userAccountRepo;
    @Autowired
    private AccountRepo accountRepo;

    public Optional<AccountModel> listarCuentasPorId(int cuenta){

        return accountRepo.findById(cuenta);
    }

    public Set<UserModel> listarTitulares(int cuenta){
        Set<UserModel> lista = new HashSet<>();
        Optional<AccountModel> optional = listarCuentasPorId(cuenta);

        if (optional.isPresent()){
            AccountModel accountModel = optional.get();
            lista = accountModel.getUserModels();
        }
        return lista;
    }

    public boolean eliminarTitular(UserModel usuario, AccountModel cuenta) {
        UserAccountIdModel id = new UserAccountIdModel(usuario.getNif(), cuenta.getNumeroCuenta());
        Optional<UserAccountModel> userAccount = userAccountRepo.findById(id);
        if (userAccount.isPresent()) {
            userAccountRepo.delete(userAccount.get());
            return true;
        }
        return false;
    }

}
