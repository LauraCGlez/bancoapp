package com.banco.bancoapp.services;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.UserRepo;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public Iterable<UserModel> listarUsuarios(){

        return userRepo.findAll();
    }

    //CREAMOS UN USUARIO
    public String crearUsuario(UserModel userModel) {
        String msg = "";
        if (userRepo.existsById(userModel.getNif())){
            msg = "El usuario ya existe. Error al crear usuario";
        } else {
            userRepo.save(userModel);
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

    //TODO ARREGLAR
    public void modificar(UserModel updatedUser) {
        try {
            UserModel dbUser = (UserModel) userRepo.findUserByNif(updatedUser.getNif());
            dbUser.setNombre(updatedUser.getNombre());
            dbUser.setApellidos(updatedUser.getApellidos());
            dbUser.setAnyoNacimiento(updatedUser.getAnyoNacimiento());
            dbUser.setDireccion(updatedUser.getDireccion());
            dbUser.setTelefono(updatedUser.getTelefono());
            dbUser.setEmail(updatedUser.getEmail());
            dbUser.setPass(updatedUser.getPass());
            userRepo.save(dbUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}