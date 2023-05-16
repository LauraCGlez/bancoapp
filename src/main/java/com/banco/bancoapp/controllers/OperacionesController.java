package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.TransactionService;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.IOException;

@Controller
public class OperacionesController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @FXML
    private TextField numeroCuentaTextField;
    @FXML
    private TextField cantidadTextField;

/*
    @FXML
    public void ingresarDinero() {
        double cantidad = Double.parseDouble(cantidadTextField.getText());
        double saldoActual = numeroCuentaTextField.getColumns();
        double nuevoSaldo = saldoActual + cantidad;
        numeroCuentaTextField.;
    }*/

    @FXML
    public void volver() throws IOException {
        BancoappApplication.switchRoot("menu");
    }
}
