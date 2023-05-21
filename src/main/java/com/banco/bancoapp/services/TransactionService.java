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
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AccountRepo accountRepo;

    public Iterable<TransactionModel> listTransactions() {
        return transactionRepo.findAll();
    }

    public void createTransaction(TransactionModel transaction) {

        try {
            boolean esTitular = false;
            UserModel titular = transaction.getTitular();
            AccountModel cuentaOrigen = transaction.getCuentaOrigen();

            UserModel titularBD = findUserByNif(titular.getNif());
            Optional <AccountModel> optionalValue = findAccountModelsByUserModelsContaining(cuentaOrigen.getNumeroCuenta());
            AccountModel cuentaOrigenBD = null;

            if (optionalValue.isPresent()) {
                cuentaOrigenBD = optionalValue.get();

                for (UserModel owner: cuentaOrigenBD.getUserModels()) {
                    if (owner.getNif().equals(titularBD.getNif())) {
                        esTitular = true;
                        break;
                    }
                }
            }

            if (esTitular) {
                transaction.setFechaOp(LocalDate.now());

                if (!transaction.getTipoOp().equals(TipoOpModel.TRANSFERIR)) {
                    transaction.setCuentaDestino(null);
                }

                if (operacion(transaction, cuentaOrigenBD)) {
                    transactionRepo.save(transaction);
                    mostrarMensaje("Transferencia creada");
                } else {
                    mostrarMensaje("Error");
                }

            } else {
                mostrarMensaje("El usuario no es titular de la cuenta");
            }

        } catch (Exception e) {
            mostrarMensaje("No se pudo realizar la transaccion");
        }
    }

    public boolean operacion(TransactionModel transactionModel, AccountModel cuentaOrigen) {
        boolean transferenciaHecha = true;
        double cantidad = transactionModel.getCantidad();
        transactionRepo.save(transactionModel);

        if (transactionModel.getTipoOp().equals(TipoOpModel.TRANSFERIR)) {
            int cuentaOrigenNumero = cuentaOrigen.getNumeroCuenta();
            int cuentaDestinoNumero = transactionModel.getCuentaDestino().getNumeroCuenta();

            if (findAccountModelsByUserModelsContaining(cuentaDestinoNumero).isPresent()) {
                AccountModel cuentaDestino = findAccountModelsByUserModelsContaining(cuentaDestinoNumero).get();
                cuentaDestino.ingresarDinero(cantidad);
                boolean retiro = cuentaOrigen.retirarDinero(cantidad);

                if (cuentaOrigenNumero != cuentaDestinoNumero) {
                    accountRepo.save(cuentaDestino);
                    accountRepo.save(cuentaOrigen);
                    transferenciaHecha = retiro;
                }
            }
        } else if (transactionModel.getTipoOp().equals(TipoOpModel.INGRESAR)) {
            cuentaOrigen.ingresarDinero(cantidad);
            accountRepo.save(cuentaOrigen);
            transferenciaHecha = true;
        } else if (transactionModel.getTipoOp().equals(TipoOpModel.RETIRAR)) {
            transferenciaHecha = cuentaOrigen.retirarDinero(cantidad);
        }
        return transferenciaHecha;
    }

    private UserModel findUserByNif(String nif) {
        List<UserModel> userList = userRepo.findUserByNif(nif);
        return userList.get(0);
    }

    public Optional<AccountModel> findAccountModelsByUserModelsContaining(int account) {
        return accountRepo.findById(account);
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}







