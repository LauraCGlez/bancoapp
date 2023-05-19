package com.banco.bancoapp.services;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.AccountRepo;
import com.banco.bancoapp.repositories.TransactionRepo;
import com.banco.bancoapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionService {
    private final String[] typeList = {"tranferir", "ingresar", "retirar"};
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AccountRepo accountRepo;

}


    /*
    public Iterable<TransactionModel> listarTransacciones(){
        return transactionRepo.findAll();
    }*/

    /*
    public double transaccion(UserModel userModel, AccountModel accountModel, double cantidad, TipoOpModel tipoOpModel){
        boolean esTitular = accountModel.getUserModels().contains(userModel);

        if (cantidad == 0){
            throw new IllegalArgumentException("La cantidad no puede ser igual a cero");
        }

        if (!esTitular){
            throw new IllegalArgumentException("El usuario no es titular de la cuenta");
        }

        if (tipoOpModel == TipoOpModel.INGRESAR){
            double nuevoSaldo = accountModel.getSaldo() + cantidad;
            accountModel.setSaldo(nuevoSaldo);
        } else if (tipoOpModel == TipoOpModel.RETIRAR){
            if (accountModel.getSaldo() >= cantidad){
                double nuevoSaldo = accountModel.getSaldo() - cantidad;
                accountModel.setSaldo(nuevoSaldo);
            } else {
                return accountModel.getSaldo();
            }
        }

        return accountModel.getSaldo();
    }*/










