package com.banco.bancoapp.services;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.UserAccountModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.AccountRepo;
import com.banco.bancoapp.repositories.UserAccounRepo;
import com.banco.bancoapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAccountService {
    @Autowired
    private UserAccounRepo userAccounRepo;
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

    public void eliminarUsuario(String nif, int cuenta){
        if (listarTitulares(cuenta).size() > 1){
            try {
                UserModel userModel = new UserModel();
                AccountModel accountModel = new AccountModel();
                //UserAccountModel userAccountModel = userAccounRepo.findByUserModelAndAccountModel(userModel.getNif(), accountModel.getNumeroCuenta());
                //userAccounRepo.save(userAccountModel);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
