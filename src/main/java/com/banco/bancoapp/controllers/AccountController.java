package com.banco.bancoapp.controllers;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.services.AccountService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @FXML
    private TableColumn<AccountModel, Integer> numeroCuentaColum;
    @FXML
    private TableColumn<AccountModel, Double> saldoColum;
    @FXML
    private TableColumn<AccountModel, LocalDate> fechaCColum;
    @FXML
    private TextField cuentaTextField;
    @FXML
    private TextField nifTextField;
    @FXML
    private TableView<TransactionModel> transactionTableView;
    @FXML
    private TableColumn<TransactionModel, Integer> codigoColumn;
    @FXML
    private TableColumn<TransactionModel, TipoOpModel> tipoOpModelTableColumn;
    @FXML
    private TableColumn<TransactionModel, LocalDate> fechaTColumn;
    @FXML
    private TableColumn<TransactionModel, Double> cantidadColumn;

    @FXML
    private void initialize() {
        // Configurar las columnas de la tabla (tableView) con los datos de las cuentas bancarias
        //TableColumn<AccountModel, Integer>
        numeroCuentaColum.setCellValueFactory(new PropertyValueFactory<>("numero de cuenta"));
        saldoColum.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        fechaCColum.setCellValueFactory(new PropertyValueFactory<>("fecha de creacion"));

        // Configurar las columnas de la tabla (transactionTableView) con los datos de las transacciones
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo transaccion"));
        tipoOpModelTableColumn.setCellValueFactory(new PropertyValueFactory<>("Tipo de transaccion"));
        fechaTColumn.setCellValueFactory(new PropertyValueFactory<>("Fecha de creacion"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad involucrada"));

    }

    /*
    @FXML
    private void buscarCuentas() {
        // Obtener el número de cuenta ingresado
        int numeroCuenta = Integer.parseInt(cuentaTextField.getText());

        // Obtener las cuentas bancarias del número de cuenta especificado
        AccountModel cuenta = accountService.listarCuentas(numeroCuenta).orElse(null);

        if (cuenta != null) {
            // Mostrar la cuenta bancaria en la tabla
            tableView.getItems().clear();
            tableView.getItems().add(cuenta);
        } else {
            // Limpiar la tabla si no se encontró ninguna cuenta
            tableView.getItems().clear();
        }
    }

    private void añadirTitular() {
        // Obtener el número de cuenta ingresado y el NIF del titular
        int numeroCuenta = Integer.parseInt(cuentaTextField.getText());
        String nif = nifTextField.getText();

        // Añadir el titular a la cuenta bancaria
        accountService.añadirUsuarioCuenta(nif, numeroCuenta);
    }

    private void eliminarTitular() {
        // Obtener el número de cuenta ingresado y el NIF del titular
        int numeroCuenta = Integer.parseInt(cuentaTextField.getText());
        String nif = nifTextField.getText();

        // Eliminar el titular de la cuenta bancaria
        accountService.eliminarUsuarioDeCuenta(nif, numeroCuenta);
    }

    /*
    private void listarOperaciones() {
        // Obtener el número de cuenta ingresado y el tipo de operación seleccionado
        int numeroCuenta = Integer.parseInt(cuentaTextField.getText());
        TipoOpModel tipoOp = tipoOpComboBox.getValue();

        // Obtener las operaciones de la cuenta bancaria según el tipo de operación seleccionado
        Set<TransactionModel> operaciones = accountService.listarOp(numeroCuenta, tipoOp);

        transactionTableView.getItems().clear();
        transactionTableView.getItems().addAll(operaciones);
    }*/
}