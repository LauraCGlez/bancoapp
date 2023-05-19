package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.TransactionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class OperacionesController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @FXML
    private Label saldoLabel;
    @FXML
    public TextField numeroCuentaTextField;
    @FXML
    public TextField cantidadTextField;

    @FXML
    private void ingresarDinero() {
        if (cantidadTextField.getText().isEmpty() || numeroCuentaTextField.getText().isEmpty()){
            mostrarMensaje("Debe rellenar los campos número de cuenta y cantidad " +
                    "para poder realizar la operación");
        } else {
            double cantidad = Double.parseDouble(cantidadTextField.getText());
            int cuenta = Integer.parseInt(numeroCuentaTextField.getText());
            double saldo = accountService.getSaldo(cuenta) ;
            accountService.actualizarSaldoIngresado(cuenta, cantidad);
            cantidadTextField.setText(String.valueOf(saldo));
            cantidadTextField.clear();
        }
    }
    @FXML
    private void retirarDinero() {
        if (cantidadTextField.getText().isEmpty() || numeroCuentaTextField.getText().isEmpty()) {
            mostrarMensaje("Debe rellenar los campos Número de cuenta y cantidad " +
                    "para poder realizar la operación");
        } else {
            double cantidad = Double.parseDouble(cantidadTextField.getText());
            int cuenta = Integer.parseInt(numeroCuentaTextField.getText());
            double saldo = accountService.getSaldo(cuenta);
            accountService.actualizarSaldoRetirado(cuenta, cantidad);

            cantidadTextField.setText(String.valueOf(saldo));
            cantidadTextField.clear();
        }
    }

    @FXML
    private void triggerSaldo(){
        int cuenta = Integer.parseInt(numeroCuentaTextField.getText());
        double saldo = accountService.getSaldo(cuenta);
        saldoLabel.setText(String.valueOf(saldo));
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void volver() throws IOException {
        BancoappApplication.switchRoot("menu");
    }
}
