package com.banco.bancoapp.services;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.AccountRepo;
import com.banco.bancoapp.repositories.TransactionRepo;
import com.banco.bancoapp.repositories.UserRepo;
import javafx.scene.control.Alert;
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
    private AccountModel accountModel;

    public Iterable<TransactionModel> operaciones(String cuenta){
        return transactionRepo.findAllByCodigoOp(cuenta);
    }

    public AccountModel findAccountByNumeroCuenta(int numeroCuenta) {
        return accountRepo.findByNumeroCuenta(numeroCuenta);
    }

    public Optional<AccountModel> listarCuentasPorId(int cuenta){

        return accountRepo.findById(cuenta);
    }

    public Iterable<AccountModel> listarCuentas(){
        return accountRepo.findAll();
    }

    public String crearCuenta(AccountModel accountModel, UserModel userModel) {
        String msg = "";
        Random random = new Random();
        int randomNumber = random.nextInt(900000000) + 1000000000;
        UserModel user = findUserByNif(userModel.getNif());

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
        Optional<AccountModel> optional = listarCuentasPorId(cuenta);

        if (optional.isPresent()){
            AccountModel accountModel = optional.get();
            lista = accountModel.getUserModels();
        }
        return lista;
    }

    public void añadirUsuarioCuenta(String nif, int cuenta){
        if (listarTitulares(cuenta).size() < 2){
            try {
                AccountModel accountModel = listarCuentasPorId(cuenta).get();
                UserModel userModel = findUserByNif(nif);
                accountModel.getUserModels().add(userModel);
                accountRepo.save(accountModel);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

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

    public  Set<TransactionModel> ingresos(int cuenta, TipoOpModel tipoOpModel){
        Set<TransactionModel> transactionModels = new HashSet<>();
        Optional<AccountModel> optional = listarCuentasPorId(cuenta);

        if (optional.isPresent()) {
            tipoOpModel.equals(TipoOpModel.INGRESAR);
            AccountModel accountModel = optional.get();
            transactionModels = accountModel.getCuentaDestino();
        }
        return transactionModels;
    }

    public Set<TransactionModel> gastos(int cuenta, TipoOpModel tipoOpModel){
        Set<TransactionModel> transactionModels = new HashSet<>();
        Optional<AccountModel> optional = listarCuentasPorId(cuenta);

        if (optional.isPresent()) {
            tipoOpModel.equals(TipoOpModel.RETIRAR);
            AccountModel accountModel = optional.get();
            transactionModels = accountModel.getCuentaOrigen();
        }
        return transactionModels;
    }

    public void actualizarSaldoIngresado(int numeroCuenta, double cantidad) {
        try {
            Optional<AccountModel> optional = accountRepo.findById(numeroCuenta);
            if (optional.isPresent()) {
                AccountModel accountModel = optional.get();
                double saldoActualizado = accountModel.getSaldo() + cantidad;
                accountModel.setSaldo(saldoActualizado);
                accountRepo.save(accountModel);
            } else {
                throw new IllegalArgumentException("La cuenta no existe");
            }
        } catch (IllegalArgumentException e) {
                mostrarMensaje(e.getMessage());
        }
    }

    public void actualizarSaldoRetirado(int numeroCuenta, double cantidad) {
        try {
            Optional<AccountModel> optional = accountRepo.findById(numeroCuenta);
            if (optional.isPresent()) {
                AccountModel accountModel = optional.get();
                double saldoActualizado = accountModel.getSaldo() - cantidad;
                if (saldoActualizado < 0) {
                    throw new IllegalArgumentException("Saldo insuficiente");
                }
                accountModel.setSaldo(saldoActualizado);
                accountRepo.save(accountModel);
            } else {
                throw new IllegalArgumentException("La cuenta no existe");
            }
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    public double getSaldo(int cuenta){
        try {
            Optional<AccountModel> optional = listarCuentasPorId(cuenta);
            if (optional.isPresent()){
                AccountModel accountModel = optional.get();
                return accountModel.getSaldo();
            }
        } catch (IllegalArgumentException e){
            mostrarMensaje("El numero de cuenta es incorrecto");
        }
        return 0;
    }

    private UserModel findUserByNif(String nif){
        List<UserModel> lista = userRepo.findUserByNif(nif);
        return lista.get(0);
    }

    public void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
