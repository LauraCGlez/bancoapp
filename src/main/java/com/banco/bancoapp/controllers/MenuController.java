package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.TransactionService;
import com.banco.bancoapp.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class MenuController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @FXML
    private Label erroresLabel;
    @FXML
    private TableView<TransactionModel> operacionesTabla;
    @FXML
    private TableColumn<TransactionModel, Integer> codigoOpColum;
    @FXML
    private TableColumn<TransactionModel, String> tipoOpColum;
    @FXML
    private TableColumn<TransactionModel, Double> cantidadColum;
    @FXML
    private TableColumn<TransactionModel, LocalDate> fechaOpColum;
    @FXML
    private TextField cuentaTextField;
    @FXML
    private TextField nifTextField;
    @FXML
    private TableView<AccountModel> tablaCuentas;
    @FXML
    private TableColumn<AccountModel, String> cuentaColumn;
    @FXML
    private TableColumn<AccountModel, Double> saldoColumn;
    @FXML
    private TableColumn<AccountModel, String> fechaColumn;
    @FXML
    private TableView<UserModel> titularesTabla;
    @FXML
    private TableColumn<UserModel, String> nombreColumn;
    @FXML
    private TableColumn<UserModel, String> nifColum;


    //APARTADO 1 ---> crear cuenta button ok
    @FXML
    public void triggerCrearCuenta() {

        if (nifTextField == null){
            String mensaje = "Debe introducir un NIF";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText(mensaje);
            alert.showAndWait();
        } else {
            String nif = nifTextField.getText();
            UserModel user = new UserModel();
            user.setNif(nif);
            String msg = crearCuenta(user);
            erroresLabel.setText(msg);
        }
    }

    //APARTADO 5, MAL PORQUE NO ESTA LISTANDO POR NIF, SINO PONIENDO TODAS ----> mis cuentas button ok
    @FXML
    public void triggerListarCuentasPorUsuarios(){
        String nif = nifTextField.getText();
        Iterable<AccountModel> accountModels = accountService.listarCuentasPorUsuarios(nif);
        tablaCuentas.getItems().clear();
        accountModels.forEach(tablaCuentas.getItems()::add);
        tablaCuentas.refresh();

    }



    //APARTADO 6 ----> transacciones button
    @FXML
    public void triggerOperaciones(){
        String numeroCuenta = cuentaTextField.getText();
        Iterable<TransactionModel> transactionModels = accountService.operaciones(numeroCuenta);
        List<TransactionModel> transactionModelList = StreamSupport.stream(transactionModels.spliterator(), false).collect(Collectors.toList());

        operacionesTabla.getItems().setAll(transactionModelList);
        operacionesTabla.refresh();
    }

    //APARTADO 3 ----> añadir titular button
    @FXML
    public void triggerAñadir(){
        int numeroCuenta = Integer.parseInt(cuentaTextField.getText());
        String nif = nifTextField.getText();
        accountService.añadirUsuarioCuenta(nif, numeroCuenta);
    }

    //TODO
    // ----> eliminar titular button
    @FXML
    private void triggerEliminar() {
        int numeroCuenta = Integer.parseInt(cuentaTextField.getText());
        String nif = nifTextField.getText();
        accountService.eliminarUsuarioDeCuenta(nif, numeroCuenta);
    }

    //----> LISTAR TITULARES DE UNA CUENTA
    @FXML
    public void listarTitulares() {
        int cuenta = Integer.parseInt(cuentaTextField.getText());
        Iterable<UserModel> titulares = accountService.listarTitulares(cuenta);
        titularesTabla.getItems().clear();
        titulares.forEach(titularesTabla.getItems()::add);
    }

    @FXML
    private void initialize() {
        cuentaColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        saldoColumn.setCellValueFactory(new PropertyValueFactory<>("saldo"));

        codigoOpColum.setCellValueFactory(new PropertyValueFactory<>("codigoOp"));
        tipoOpColum.setCellValueFactory(new PropertyValueFactory<>("tipoOp"));
        cantidadColum.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fechaOpColum.setCellValueFactory(new PropertyValueFactory<>("fechaOp"));

        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nifColum.setCellValueFactory(new PropertyValueFactory<>("nif"));
    }

    public String crearCuenta(UserModel userModel) {
        return accountService.crearCuenta(new AccountModel(), userModel);
    }



    //APARTADO 7

    //APARTADO 8

    @FXML
    public void volver() throws IOException{
        BancoappApplication.switchRoot("login");
    }

    @FXML
    public void operar() throws IOException{
        BancoappApplication.switchRoot("operaciones");
    }

}
