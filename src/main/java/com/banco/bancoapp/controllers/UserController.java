package com.banco.bancoapp.controllers;

import com.banco.bancoapp.BancoappApplication;
import com.banco.bancoapp.models.AccountModel;
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
    private TextField nifTF;
    @PersistenceContext
    private EntityManager entityManager;

    @FXML
    private void eliminarUsuario() {
        if (nifTF == null) {
            String mensaje = "Debe introducir un NIF";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mensaje");
            alert.setContentText(mensaje);
            alert.showAndWait();
        } else {
            String nif = nifTF.getText();
            UserModel user = userService.listarUsuarios(nif);

        }
    }


    @FXML
    private void initialize() {
        nifColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("nif"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("nombre"));
        apellidosColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("apellidos"));
        anyoNacimientoColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("anyoNacimiento"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("direccion"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("email"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<UserModel, Integer>("telefono"));
    }

    //APARTADO 4
    @FXML
    private void listarUsuarios() {
        // Obtener la lista de usuarios del servicio
        Iterable<UserModel> usuarios = userService.listarUsuarios();

        // Limpiar la tabla
        userTable.getItems().clear();

        // Agregar los usuarios a la tabla
        usuarios.forEach(userTable.getItems()::add);
    }



/*
    @FXML
    private void modificarUsuario() {
        // Obtener el usuario seleccionado en la tabla
        UserModel usuarioSeleccionado = userTable.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado != null) {
            // Realizar la modificación del usuario utilizando el servicio
            userService.modificarUsuario(usuarioSeleccionado);

            // Mostrar un mensaje de éxito
            mostrarMensaje("Usuario modificado correctamente");
            userTable.refresh();
        } else {
            // Mostrar un mensaje de error si no se seleccionó ningún usuario
            mostrarMensaje("Seleccione un usuario para modificar");
        }
    }*/
    /*

    @FXML
    private void eliminarUsuario() {
        // Obtener el usuario seleccionado en la tabla
        UserModel usuarioSeleccionado = userTable.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado != null) {
            // Obtener el NIF del usuario seleccionado
            String nif = usuarioSeleccionado.getNif();

            // Eliminar el usuario utilizando el servicio
            userService.eliminarUsuario(nif);

            // Mostrar un mensaje de éxito
            mostrarMensaje("Usuario eliminado correctamente");

            // Actualizar la tabla de usuarios
            listarUsuarios();
            userTable.refresh();
        } else {
            // Mostrar un mensaje de error si no se seleccionó ningún usuario
            mostrarMensaje("Seleccione un usuario para eliminar");
        }
    }


     */

    //OJO CODIGO NUEVO SIN
    //APARTADO 2

    @FXML
    private void modificarUsuario() {
        // Obtener el usuario seleccionado en la tabla
        UserModel usuarioSeleccionado = userTable.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado != null) {
            // Abrir la ventana de modificación de usuario
            abrirVentanaModificarUsuario(usuarioSeleccionado);
        } else {
            // Mostrar un mensaje de error si no se seleccionó ningún usuario
            mostrarMensaje("Seleccione un usuario para modificar");
        }
    }

    private void abrirVentanaModificarUsuario(UserModel usuario) {
        try {
            // Cargar el archivo FXML de la ventana de modificación de usuario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modificar.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de modificación de usuario
            ModificarUsuarioController modificarUsuarioController = loader.getController();

            // Pasar el usuario seleccionado al controlador de la ventana de modificación
            modificarUsuarioController.setUsuario(usuario);

            // Crear una nueva ventana y mostrarla
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Usuario");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    @FXML
    public void triggerListarCuentasPorUsuarios(){
        Iterable<AccountModel> accountModels = accountService.listarCuentas();
        tablaCuentas.getItems().clear();
        accountModels.forEach(tablaCuentas.getItems()::add);
        tablaCuentas.refresh();

    }*/


    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void volver() throws IOException{
        BancoappApplication.switchRoot("login");
    }
}
