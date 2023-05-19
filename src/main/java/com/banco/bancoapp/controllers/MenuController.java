package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.TransactionService;
import com.banco.bancoapp.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.banco.bancoapp.services.UserService.extracto;

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
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField añoNacimientoTextField;
    @FXML
    private TextField direccionTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private TextField passTextField;

    @FXML
    private void initialize() {
        cuentaColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        saldoColumn.setCellValueFactory(new PropertyValueFactory<>("saldo"));

        /*
        codigoOpColum.setCellValueFactory(new PropertyValueFactory<>("codigoOp"));
        tipoOpColum.setCellValueFactory(new PropertyValueFactory<>("tipoOp"));
        cantidadColum.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fechaOpColum.setCellValueFactory(new PropertyValueFactory<>("fechaOp"));

         */
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nifColum.setCellValueFactory(new PropertyValueFactory<>("nif"));
    }

    @FXML
    public void triggerCrearCuenta() {

        if (nifTextField == null) {
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

    @FXML
    public void triggerListarCuentasPorUsuarios() {
        String nif = nifTextField.getText();
        Iterable<AccountModel> accountModels = accountService.listarCuentasPorUsuarios(nif);
        tablaCuentas.getItems().clear();
        accountModels.forEach(tablaCuentas.getItems()::add);
        tablaCuentas.refresh();
    }

    @FXML
    public void triggerAñadir() {
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
        accountService.eliminarUsuario(nif, numeroCuenta);
    }

    @FXML
    public void listarTitulares() {
        int cuenta = Integer.parseInt(cuentaTextField.getText());
        if (!cuentaTextField.equals(null)) {
            Iterable<UserModel> titulares = accountService.listarTitulares(cuenta);
            titularesTabla.getItems().clear();
            titulares.forEach(titularesTabla.getItems()::add);
        } else {
            accountService.mostrarMensaje("Debe ingresar un numero de cuenta");
        }
    }

    public String crearCuenta(UserModel userModel) {
        return accountService.crearCuenta(new AccountModel(), userModel);
    }

    @FXML
    public void volver() throws IOException {
        BancoappApplication.switchRoot("login");
    }

    @FXML
    public void operar() throws IOException {
        BancoappApplication.switchRoot("operaciones");
    }

    @FXML
    public void modificar() throws IOException{
        BancoappApplication.switchRoot("modificar");
    }

}
