package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @FXML
    private TableView<UserModel> userTable;
    @FXML
    private TableColumn<UserModel, String> nifColumn;
    @FXML
    private TableColumn<UserModel, String> nombreColumn;
    @FXML
    private TableColumn<UserModel, String> apellidosColumn;
    @FXML
    private TableColumn<UserModel, String> anyoNacimientoColumn;
    @FXML
    private TableColumn<UserModel, String> direccionColumn;
    @FXML
    private TableColumn<UserModel, String> emailColumn;
    @FXML
    private TableColumn<UserModel, Integer> telefonoColumn;
    @FXML
    private TableColumn<UserModel, String> passColumn;
    @FXML
    private TextField nifTF;
    @FXML
    private TableView<AccountModel> accountTable;
    @FXML
    private TableColumn<AccountModel, Integer> numeroColumn;
    @FXML
    private TableColumn<AccountModel, Double> saldoColumn;
    @FXML
    private TableColumn<AccountModel, String> fechaColumn;

    @FXML
    private void initialize() {
        nifColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("nif"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("nombre"));
        apellidosColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("apellidos"));
        anyoNacimientoColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("anyoNacimiento"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("direccion"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("email"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<UserModel, Integer>("telefono"));
        passColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("pass"));

        numeroColumn.setCellValueFactory(new PropertyValueFactory<AccountModel, Integer>("numeroCuenta"));
        saldoColumn.setCellValueFactory(new PropertyValueFactory<AccountModel, Double>("saldo"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<AccountModel, String>("fechaCreacion"));
    }

    //APARTADO 4
    @FXML
    private void listarUsuarios() {
        Iterable<UserModel> usuarios = userService.listarUsuarios();
        userTable.getItems().clear();
        usuarios.forEach(userTable.getItems()::add);
    }

    //TODO listar operaciones de una determinada cuenta
    @FXML
    public void listarOperaciones(){
        /*String numeroCuenta = cuentaTextField.getText();
        Iterable<TransactionModel> transactionModels = accountService.operaciones(numeroCuenta);
        List<TransactionModel> transactionModelList = StreamSupport.stream(transactionModels.spliterator(), false).collect(Collectors.toList());

        operacionesTabla.getItems().setAll(transactionModelList);
        operacionesTabla.refresh();*/
    }

    //TODO listar las cuentas de un determinado usuario
    @FXML
    private void listarCuentas(){
        Iterable<AccountModel> cuentas = accountService.listarCuentas();
        accountTable.getItems().clear();
        cuentas.forEach(cuenta -> accountTable.getItems().add(cuenta));
    }
    @FXML
    public void volver() throws IOException{
        BancoappApplication.switchRoot("login");
    }
}
