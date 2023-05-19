package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.AccountService;
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
    @Autowired
    private AccountService accountService;

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
    @FXML
    private TextField passTextField;
    private Stage stage;

    @FXML
    public void crearUsuario() {
        String nif = nifTextField.getText();
        String nombre = nombreTextField.getText();
        String apellidos = apellidosTextField.getText();
        String añoNacimiento = añoNacimientoTextField.getText();
        String direccion = direccionTextField.getText();
        String email = emailTextField.getText();
        int telefono = Integer.parseInt(telefonoTextField.getText());
        String pass = passTextField.getText();

        try {
            if (nif.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || añoNacimiento.isEmpty() || direccion.isEmpty() || email.isEmpty() || pass.isEmpty() || telefonoTextField.getText().isEmpty()) {
                accountService.mostrarMensaje("No puede haber campos vacíos");
                return; // Sale del método si hay campos vacíos
            }

            if (nif.length() != 9) {
                accountService.mostrarMensaje("NIF incorrecto");
                return; // Sale del método si el NIF es incorrecto
            }

            if (!validarEmail(email)) {
                accountService.mostrarMensaje("Formato de email incorrecto");
                return; // Sale del método si el formato de email es incorrecto
            }

            UserModel userModel = new UserModel();
            userModel.setNif(nif);
            UserService.extracto(nombre, apellidos, añoNacimiento, direccion, email, telefono, pass, userModel, userService, nifTextField, nombreTextField, apellidosTextField, añoNacimientoTextField, direccionTextField, emailTextField, telefonoTextField, passTextField);

            accountService.mostrarMensaje("Usuario creado correctamente");
            stage.close();
        } catch (NumberFormatException e) {
            accountService.mostrarMensaje("Teléfono debe ser un número válido");
        } catch (RuntimeException e) {
            accountService.mostrarMensaje("Error al crear el usuario");
        }
    }

    private boolean  validarEmail(String email){
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    @FXML
    public void volver() throws IOException {
        BancoappApplication.switchRoot("login");
    }
}
