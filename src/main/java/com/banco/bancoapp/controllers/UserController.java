package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.TransactionService;
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
import java.time.LocalDate;
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
    @Autowired
    private TransactionService transactionService;

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
    private TableView<TransactionModel> operacionesTable;
    @FXML
    private TableColumn<TransactionModel, String> tipoOperacionColumn;
    @FXML
    private TableColumn<TransactionModel, LocalDate> fechaRealizacionColumn;
    @FXML
    private TableColumn<TransactionModel, Double> cantidadColumn;
    @FXML
    private TableColumn<TransactionModel, AccountModel> cuentaOrigenColumn;
    @FXML
    private TableColumn<TransactionModel, AccountModel> cuentaDestinoColumn;
    @FXML
    private TableColumn<TransactionModel, UserModel> titularColumn;

    @FXML
    private void initialize() {
        nifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidosColumn.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        anyoNacimientoColumn.setCellValueFactory(new PropertyValueFactory<>("anyoNacimiento"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        passColumn.setCellValueFactory(new PropertyValueFactory<>("pass"));

        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        saldoColumn.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));

        tipoOperacionColumn.setCellValueFactory(new PropertyValueFactory<>("tipoOp"));
        fechaRealizacionColumn.setCellValueFactory(new PropertyValueFactory<>("fechaOp"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        cuentaOrigenColumn.setCellValueFactory(new PropertyValueFactory<>("cuentaOrigenStr"));
        cuentaDestinoColumn.setCellValueFactory(new PropertyValueFactory<>("cuentaDestinoStr"));
        titularColumn.setCellValueFactory(new PropertyValueFactory<>("titularStr"));
    }

    @FXML
    private void listarUsuarios() {
        Iterable<UserModel> usuarios = userService.listarUsuarios();
        userTable.getItems().clear();
        usuarios.forEach(userTable.getItems()::add);
    }

    @FXML
    public void listarOperaciones() {
        Iterable<TransactionModel> transactionModels = transactionService.listTransactions();
        operacionesTable.getItems().clear();
        transactionModels.forEach(transaction -> {
            String cuentaOrigen = String.valueOf(transaction.getCuentaOrigen().getNumeroCuenta());
            String cuentaDestino = String.valueOf(transaction.getCuentaDestino().getNumeroCuenta());
            //String titular = transaction.getTitular().getNombre();
            transaction.setCuentaOrigenStr(cuentaOrigen);
            transaction.setCuentaDestinoStr(cuentaDestino);
            //transaction.setTitularStr(titular);
            operacionesTable.getItems().add(transaction);
        });

    }

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

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
