package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.services.AccountService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Set;

@Controller
public class BalanceController {

    @Autowired
    public AccountService accountService;
    @FXML
    private TableView<AccountService> tablaBalance;
    @FXML
    private TableColumn<AccountService, Double> ingresosColum;
    @FXML
    private TableColumn<AccountService, Double> gastosColum;
    @FXML
    private TextField numeroCuentaTextField;
    @FXML
    private Label saldoLabel;
    @FXML
    private void initialize(){
        ingresosColum.setCellValueFactory(new PropertyValueFactory<>("canidad"));
        gastosColum.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
    }

    private void triggerIngresos(){
        //    public void listarTitulares() {
        //        int cuenta = Integer.parseInt(cuentaTextField.getText());
        //        Iterable<UserModel> titulares = accountService.listarTitulares(cuenta);
        //        titularesTabla.getItems().clear();
        //        titulares.forEach(titularesTabla.getItems()::add);
        //    }
        int cuenta = Integer.parseInt(numeroCuentaTextField.getText());
        Set<TransactionModel> transactionModels = accountService.ingresos(cuenta, TipoOpModel.INGRESAR);
        tablaBalance.getItems().setAll((AccountService) transactionModels);
    }

    private void triggerGastos(){
        int cuenta = Integer.parseInt(numeroCuentaTextField.getText());
        Set<TransactionModel> transactionModels = accountService.gastos(cuenta,TipoOpModel.RETIRAR);
        tablaBalance.getItems().setAll((AccountService) transactionModels);

    }


    //----> MUESTRA EL SALDO DE LA CUENTS button Mostrar saldo ok
    @FXML
    private void triggerSaldo(){
        int cuenta = Integer.parseInt(numeroCuentaTextField.getText());
        double saldo = accountService.getSaldo(cuenta);
        saldoLabel.setText(String.valueOf(saldo));
    }


    @FXML
    public void volver() throws IOException {
        BancoappApplication.switchRoot("login");
    }


}
