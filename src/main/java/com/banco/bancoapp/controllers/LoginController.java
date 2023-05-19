package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.UserRepo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private UserRepo userRepo;
    @FXML
    public TextField campoNombre;
    @FXML
    public PasswordField passwordField;

    public void login() throws IOException{
        String nombre = campoNombre.getText();
        String pass = passwordField.getText();
        UserModel userModel = userRepo.findUserModelByNombreIgnoreCaseAndPass(nombre, pass);

        if (nombre.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")){
            BancoappApplication.switchRoot("user");
        } else if (userModel != null){
            BancoappApplication.switchRoot("menu");
        } else {
            mensaje();
        }
    }

    private void mensaje(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Usuario o contraseña incorrectos");
        alert.showAndWait();
    }

    public void registrarse() throws IOException{
        BancoappApplication.switchRoot("registro");
    }
}
