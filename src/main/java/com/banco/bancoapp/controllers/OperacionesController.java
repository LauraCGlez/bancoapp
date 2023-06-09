package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.TransactionService;
import com.banco.bancoapp.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Controller
public class OperacionesController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @FXML
    private Label saldoLabel;
    @FXML
    public TextField cuentaOrigenTextField;
    @FXML
    public TextField cuentaDestinoTextField;
    @FXML
    public TextField cantidadTextField;
    @FXML
    public ChoiceBox<TipoOpModel> tipoOpChoiceBox;


    @FXML
    private void triggerOperacion() {
        int cuentaOrigen = Integer.parseInt(cuentaOrigenTextField.getText());
        double cantidad = Double.parseDouble(cantidadTextField.getText());
        int cuentaDestino = Integer.parseInt(cuentaDestinoTextField.getText());
        TipoOpModel tipoOpModel = tipoOpChoiceBox.getValue();
        AccountModel cuentaOrigenBD = accountService.findAccountByNumeroCuenta(cuentaOrigen);
        AccountModel cuentaDestinoBD = accountService.findAccountByNumeroCuenta(cuentaDestino);

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setCantidad(cantidad);
        transactionModel.setTipoOp(tipoOpModel);
        transactionModel.setCuentaOrigen(cuentaOrigenBD);
        transactionModel.setFechaOp(LocalDate.now());
        transactionModel.setCuentaDestino(cuentaDestinoBD);

        boolean transferenciaHecha = transactionService.operacion(transactionModel, cuentaOrigenBD);
        if (transferenciaHecha) {
            mostrarMensaje("Operación realizada exitosamente");
        } else {
            mostrarMensaje("No se pudo realizar la operación");
        }
    }

    @FXML
    private void triggerSaldo() {
        int cuenta = Integer.parseInt(cuentaOrigenTextField.getText());
        double saldo = accountService.getSaldo(cuenta);
        saldoLabel.setText(String.valueOf(saldo));
    }

    public void mostrarMensaje(String mensaje) {
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



    public void initialize() {
        tipoOpChoiceBox.getItems().setAll(TipoOpModel.values());
    }

}
