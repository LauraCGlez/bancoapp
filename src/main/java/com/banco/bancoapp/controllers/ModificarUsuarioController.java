package com.banco.bancoapp.controllers;

import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ModificarUsuarioController {

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

    private UserModel usuario;

    public ModificarUsuarioController() {
    }

    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;

        // Mostrar los datos del usuario en los campos de entrada
        nifTextField.setText(usuario.getNif());
        nombreTextField.setText(usuario.getNombre());
        apellidosTextField.setText(usuario.getApellidos());
        añoNacimientoTextField.setText(usuario.getAnyoNacimiento());
        direccionTextField.setText(usuario.getDireccion());
        emailTextField.setText(usuario.getEmail());
        telefonoTextField.setText(String.valueOf(usuario.getTelefono()));
    }

    @FXML
    private void guardarCambios() {
        // Obtener los valores modificados de los campos de entrada
        String nuevoNombre = nombreTextField.getText();
        String nuevosApellidos = apellidosTextField.getText();
        String nuevoAñoNacimiento =añoNacimientoTextField.getText();
        String nuevaDireccion = direccionTextField.getText();
        String nuevoEmail = emailTextField.getText();
        String nuevoTelefono = String.valueOf(telefonoTextField);

        // Actualizar los datos del usuario
        usuario.setNombre(nuevoNombre);
        usuario.setApellidos(nuevosApellidos);
        usuario.setAnyoNacimiento(nuevoAñoNacimiento);
        usuario.setDireccion(nuevaDireccion);
        usuario.setEmail(nuevoEmail);
        usuario.setTelefono(Integer.parseInt(nuevoTelefono));

        // Llamar al método de UserService para modificar el usuario
        userService.modificarUsuario(usuario);

        // Mostrar un mensaje de éxito
       // mostrarMensaje("Usuario modificado correctamente");

        // Cerrar la ventana de modificación de usuario
        Stage stage = (Stage) nifTextField.getScene().getWindow();
        stage.close();
    }


}