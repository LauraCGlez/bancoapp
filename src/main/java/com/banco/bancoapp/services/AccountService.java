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
import java.util.*;

@Service
public class AccountService {
    private int intentos = 0;

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TransactionRepo transactionRepo;

    public Iterable<TransactionModel> operaciones(String cuenta){
        return transactionRepo.findAllByCodigoOp(cuenta);
    }

    //LISTAR CUENTAS POR UN IDENTIFICADOR
    public Optional<AccountModel> listarCuentas(int cuenta){

        return accountRepo.findById(cuenta);
    }

    //LISTAR TODAS LAS CUENTAS
    public Iterable<AccountModel> listarCuentas(){

        return accountRepo.findAll();
    }


    //APARTADO 1
    public String crearCuenta(AccountModel accountModel, UserModel userModel) {
        String msg = "";
        Random random = new Random();
        int randomNumber = random.nextInt(900000000) + 1000000000;
        UserModel user = findUserByNif(userModel.getNif());

        //CREAMOS LA CUENTA
        accountModel.setNumeroCuenta(randomNumber);
        accountModel.getUserModels().add(user);
        accountModel.setFechaCreacion(LocalDate.now().toString());

        if (user == null){
            msg = "No hay usuario";
        } else {
            try {
                accountRepo.save(accountModel);
                intentos = 0;
            } catch (Exception e){
                if (intentos < 2){
                    intentos++;
                    crearCuenta(accountModel, userModel);
                } else {
                   msg = "No se ha creado la cuenta";
                }
            }
        }

        return msg;
    }

    public Set<UserModel> listarTitulares(int cuenta){
        Set<UserModel> lista = new HashSet<>();
        Optional<AccountModel> optional = listarCuentas(cuenta);

        if (optional.isPresent()){
            AccountModel accountModel = optional.get();
            lista = accountModel.getUserModels();
        }
        return lista;
    }

    //APARTADO 3
    //ELIMINAR TITULAR DE LA CUENTA
    public void eliminarUsuarioDeCuenta(String nif, int cuenta){
                AccountModel accountModel = listarCuentas(cuenta).get();
                UserModel userModel = findUserByNif(nif);
                Set<UserModel> userModels = accountModel.getUserModels();
//                List<UserModel> userList = new ArrayList<>(userModels);
                userModels.remove(userModel);
                accountRepo.save(accountModel);
                /*
                accountModel.getUserModels().remove(userModel);
                accountRepo.delete(accountModel);*/




    }

    //AÑADIR TITULAR A LA CUENTA
    public void añadirUsuarioCuenta(String nif, int cuenta){
        if (listarTitulares(cuenta).size() < 2){
            try {
                AccountModel accountModel = listarCuentas(cuenta).get();
                UserModel userModel = findUserByNif(nif);
                accountModel.getUserModels().add(userModel);
                accountRepo.save(accountModel);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //APARTADO 5
    public List<AccountModel> listarCuentasPorUsuarios(String nif){
        List<AccountModel> accountModels = new ArrayList<>();

        try {
            UserModel userModel = findUserByNif(nif);
            accountModels = accountRepo.findAccountModelsByUserModelsContaining(userModel);
        } catch (Exception e){
            e.printStackTrace();
        }

        return accountModels;
    }

    /*
    public Set<TransactionModel> listarOp(int cuenta){
        Set<TransactionModel> transactionModelsO = new HashSet<>();
        Set<TransactionModel> transactionModelsD = new HashSet<>();

        Optional<AccountModel> optional = listarCuentas(cuenta);

        if (optional.isPresent()){
            AccountModel accountModel = optional.get();
            transactionModelsO = accountModel.getTransactionOrigen();
            transactionModelsD = accountModel.getTransactionDestino();
        }

        return (transactionModelsO, transactionModelsD);
    }*/

    //APARTADO 6
    /*
    public Set<TransactionModel> listarOp(int cuenta, TipoOpModel tipoOpModel){
        Set<TransactionModel> transactionModels = new HashSet<>();
        Optional<AccountModel> optional = listarCuentas(cuenta);

        if (optional.isPresent()){
            AccountModel accountModel = optional.get();

            if (tipoOpModel == TipoOpModel.INGRESAR){
                transactionModels = accountModel.getTransactionDestino();
            } else if(tipoOpModel == TipoOpModel.RETIRAR){
                transactionModels = accountModel.getTransactionOrigen();
            }
        }

        return transactionModels;
    }*/

    public  Set<TransactionModel> ingresos(int cuenta, TipoOpModel tipoOpModel){
        Set<TransactionModel> transactionModels = new HashSet<>();
        Optional<AccountModel> optional = listarCuentas(cuenta);

        if (optional.isPresent()) {
            tipoOpModel.equals(TipoOpModel.INGRESAR);
            AccountModel accountModel = optional.get();
            transactionModels = accountModel.getCuentaDestino();
        }
        return transactionModels;
    }



    public Set<TransactionModel> gastos(int cuenta, TipoOpModel tipoOpModel){
        Set<TransactionModel> transactionModels = new HashSet<>();
        Optional<AccountModel> optional = listarCuentas(cuenta);

        if (optional.isPresent()) {
            tipoOpModel.equals(TipoOpModel.RETIRAR);
            AccountModel accountModel = optional.get();
            transactionModels = accountModel.getCuentaOrigen();
        }
        return transactionModels;
    }

    public double getSaldo(int cuenta){
        Optional<AccountModel> optional = listarCuentas(cuenta);
        if (optional.isPresent()){
            AccountModel accountModel = optional.get();
            return accountModel.getSaldo();
        }
        return 0;
    }

    public void actualizarSaldoIngresado(int numeroCuenta, double cantidad) {
        Optional<AccountModel> optional = accountRepo.findById(numeroCuenta);
        if (optional.isPresent()) {
            AccountModel accountModel = optional.get();
            double saldoActualizado = accountModel.getSaldo() + cantidad;
            accountModel.setSaldo(saldoActualizado);
            accountRepo.save(accountModel);
        } else {
            throw new IllegalArgumentException("La cuenta no existe");
        }
    }
    public void actualizarSaldoRetirado(int numeroCuenta, double cantidad) {
        Optional<AccountModel> optional = accountRepo.findById(numeroCuenta);
        if (optional.isPresent()) {
            AccountModel accountModel = optional.get();
            double saldoActualizado = accountModel.getSaldo() - cantidad;
            accountModel.setSaldo(saldoActualizado);
            accountRepo.save(accountModel);
        } else {
            throw new IllegalArgumentException("La cuenta no existe");
        }
    }




    private UserModel findUserByNif(String nif){
        List<UserModel> lista = userRepo.findUserByNif(nif);
        return lista.get(0);
    }



}


