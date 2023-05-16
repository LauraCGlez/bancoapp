package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class RegistroController {
    @Autowired
    private UserService userService;

    @FXML
    private TextField nifTextField;
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
    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void crearUsuario(){
        String nif = nifTextField.getText();
        String nombre = nombreTextField.getText();
        String apellidos = apellidosTextField.getText();
        String añoNacimiento = añoNacimientoTextField.getText();
        String direccion = direccionTextField.getText();
        String email = emailTextField.getText();
        int telefono = Integer.parseInt(telefonoTextField.getText());

        UserModel userModel = new UserModel();
        userModel.setNif(nif);
        userModel.setNombre(nombre);
        userModel.setApellidos(apellidos);
        userModel.setAnyoNacimiento(añoNacimiento);
        userModel.setDireccion(direccion);
        userModel.setEmail(email);
        userModel.setTelefono(telefono);

        try {
            String msg = userService.crearUsuario(userModel);
//            label.setText(msg);
            nifTextField.clear();
            nombreTextField.clear();
            apellidosTextField.clear();
            añoNacimientoTextField.clear();
            direccionTextField.clear();
            emailTextField.clear();
            telefonoTextField.clear();

            mensaje("El usuario se ha creado correctamente");

            stage.close();

        } catch (RuntimeException e){
            System.out.println("Error al crear el usuario: " + e.getMessage());
        }
    }

    private void mensaje(String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void volver() throws IOException {
        BancoappApplication.switchRoot("login");
    }
}
