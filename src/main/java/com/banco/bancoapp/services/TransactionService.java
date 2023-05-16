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

    public Iterable<TransactionModel> operaciones(String cuenta){

        return transactionRepo.findAll();
    }

    private UserModel findUserByNif(String nif){
        List<UserModel> lista = userRepo.findUserByNif(nif);
        return lista.get(0);
    }

    public Optional<AccountModel> listarCuentas(int cuenta){

        return accountRepo.findById(cuenta);
    }


    //APARTADO 7
    public void transaccion(TransactionModel transactionModel){
        try {
            boolean titular = false;
            UserModel userModel = transactionModel.getUser();
            AccountModel cuentaOrigen = transactionModel.getCuentaOrigen();
            UserModel userModel1 = findUserByNif(userModel.getNif());
            Optional<AccountModel> optional = listarCuentas(cuentaOrigen.getNumeroCuenta());
            AccountModel cuentaOrigen1 = null;

            if (optional.isPresent()){
                cuentaOrigen1 = optional.get();

                for (UserModel u: cuentaOrigen1.getUserModels()){
                    if (u.getNif().equals(userModel1.getNif())){
                        titular = true;
                        break;
                    }
                }
            }

            if (titular){
                transactionModel.setFechaOp(LocalDate.now());

                if (!transactionModel.getTipoOp().equals(TipoOpModel.TRANSFERIR)){
                    transactionModel.setCuentaDestino(null);
                }

                if (plusTransaccion(transactionModel, cuentaOrigen1)){
                    transactionRepo.save(transactionModel);
                    System.out.println("Transaccion exitosa");
                } else {
                    System.out.println("No se ha podido realizar la transaccion");
                }


            } else {
                System.out.println("El usuario no es titular de la cuenta");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //APARTADO 8
    private boolean plusTransaccion(TransactionModel transactionModel, AccountModel cuentaOrigen) {
        boolean hecho = false;
        double cantidad = transactionModel.getCantidad();

        if (typeList[0].equals(transactionModel.getTipoOp().toString())) {
            int cuentaOrigenN = cuentaOrigen.getNumeroCuenta();
            int cuentaDestinoN = transactionModel.getCuentaDestino().getNumeroCuenta();

            if (listarCuentas(cuentaDestinoN).isPresent()){
                AccountModel cuentaDestino = listarCuentas(cuentaDestinoN).get();
                cuentaDestino.ingresarDinero(cantidad);
                boolean retirar = cuentaOrigen.retirarDinero(cantidad);

                if (cuentaOrigenN != cuentaDestinoN){
                    accountRepo.save(cuentaDestino);
                    accountRepo.save(cuentaOrigen);
                    hecho = retirar;
                }

            }
        } else if (typeList[1].equals(transactionModel.getTipoOp().toString())) {
            cuentaOrigen.ingresarDinero(cantidad);
            accountRepo.save(cuentaOrigen);
            hecho = true;
        } else if (typeList[2].equals(transactionModel.getTipoOp().toString())) {
            hecho = cuentaOrigen.retirarDinero(cantidad);
        }

        return hecho;
    }

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










