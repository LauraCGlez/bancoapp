package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;

@Controller
public class LoginController {

    @FXML
    public TextField campoNombre;
    @FXML
    public TextField campoEmail;

    //VERIFICAR SI EL USUARIO EXISTE SEGUN NOMBRE Y EMAIL

    public void login() throws IOException{
        String nombre = campoNombre.getText();
        String email = campoEmail.getText();


        if (validarEmail(email)){
            if (nombre.equals("admin")){
                BancoappApplication.switchRoot("user");
            } else{
                BancoappApplication.switchRoot("menu");
            }
        } else {
            mensaje("Email incorrecto");
        }
    }

    private boolean  validarEmail(String email){
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    private void mensaje(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void registrarse() throws IOException{
        BancoappApplication.switchRoot("registro");
    }
}
