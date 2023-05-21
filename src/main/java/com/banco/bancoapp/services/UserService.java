package com.banco.bancoapp.services;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.UserRepo;
import javafx.scene.control.TextField;
import org.apache.catalina.User;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public Iterable<UserModel> listarUsuarios(){

        return userRepo.findAll();
    }

    public UserModel findUserByNif(String nif) {
        return userRepo.findByNif(nif);
    }

    public String crearUsuario(UserModel userModel) {
        String msg;
        try {
            if (userRepo.existsById(userModel.getNif())){
                msg = "El usuario ya existe. Error al crear usuario";
            } else {
                userRepo.save(userModel);
                msg = "Usuario guardado";
            }
        } catch (DataAccessException e){
            msg = "Error al crear el usuario: " + e.getMessage();
        }
        return msg;
    }

    public static void extracto(String nombre, String apellidos, String añoNacimiento, String direccion, String email, int telefono, String pass, UserModel userModel, UserService userService, TextField nifTextField, TextField nombreTextField, TextField apellidosTextField, TextField añoNacimientoTextField, TextField direccionTextField, TextField emailTextField, TextField telefonoTextField, TextField passTextField) {
        userModel.setNombre(nombre);
        userModel.setApellidos(apellidos);
        userModel.setAnyoNacimiento(añoNacimiento);
        userModel.setDireccion(direccion);
        userModel.setEmail(email);
        userModel.setTelefono(telefono);
        userModel.setPass(pass);

        userService.crearUsuario(userModel);

        nifTextField.clear();
        nombreTextField.clear();
        apellidosTextField.clear();
        añoNacimientoTextField.clear();
        direccionTextField.clear();
        emailTextField.clear();
        telefonoTextField.clear();
        passTextField.clear();
    }

    public String updateUser(UserModel userModel) {
        String msg;
        try {
            if (userRepo.findById(userModel.getNif()).isPresent()) {
                userRepo.save(userModel);
                msg = "El usuario ha sido modificado!";
            } else {
                msg = "No hay ningún usuario registrado con ese NIF";
            }
        } catch (DataAccessException ex) {
            msg = "Error! No se pudo modificar el usuario" + ex.getMessage();
        }
        return msg;
    }
}