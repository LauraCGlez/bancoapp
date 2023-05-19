package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.UserRepo;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static com.banco.bancoapp.services.UserService.extracto;

@Controller
public class ModificarUsuarioController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RegistroController registroController;
    private UserModel userModel;
    @Autowired
    private AccountService accountService;
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
    private TextField nifTextField;
    @Autowired
    private MenuController menuController;

    @FXML
    private void modificarUsuario () throws IOException {
        String nif = nifTextField.getText();
        String nombre = nombreTextField.getText();
        String apellidos = apellidosTextField.getText();
        String añoNacimiento = añoNacimientoTextField.getText();
        String direccion = direccionTextField.getText();
        String email = emailTextField.getText();
        int telefono = Integer.parseInt(telefonoTextField.getText());
        String pass = passTextField.getText();

        // Crear un objeto UserModel con los valores actualizados
        UserModel updatedUser = new UserModel();
        updatedUser.setNif(nif);
        updatedUser.setNombre(nombre);
        updatedUser.setApellidos(apellidos);
        updatedUser.setAnyoNacimiento(añoNacimiento);
        updatedUser.setDireccion(direccion);
        updatedUser.setEmail(email);
        updatedUser.setTelefono(telefono);
        updatedUser.setPass(pass);

        // Llamar al método modificar del servicio UserService
        userService.modificar(updatedUser);

        BancoappApplication.switchRoot("menu");
    }

}