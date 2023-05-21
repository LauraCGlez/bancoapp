package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.UserRepo;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Optional;

import static com.banco.bancoapp.services.UserService.extracto;

@Controller
public class ModificarUsuarioController {

    @Autowired
    OperacionesController operacionesController;
    @Autowired
    private UserService userService;
    private UserModel userModel;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField anyoNacimientoTextField;
    @FXML
    private TextField direccionTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private PasswordField passField;
    @FXML
    private TextField nifTextField;

    @FXML
    private void modificarUsuario() throws IOException{
        UserModel userModel = new UserModel();
        userModel.setNif(nifTextField.getText());
        userModel.setEmail(emailTextField.getText());
        userModel.setNombre(nombreTextField.getText());
        userModel.setApellidos(apellidosTextField.getText());
        userModel.setDireccion(direccionTextField.getText());
        userModel.setAnyoNacimiento(anyoNacimientoTextField.getText());
        userModel.setTelefono(Integer.parseInt(telefonoTextField.getText()));
        userModel.setPass(passField.getText());

        String msg = userService.updateUser(userModel);
        mostrarMensaje(msg);

        BancoappApplication.switchRoot("menu");
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}