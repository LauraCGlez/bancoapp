package com.banco.bancoapp.services;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public Iterable<UserModel> listarUsuarios(){

        return userRepo.findAll();
    }

    //CREAMOS UN USUARIO
    public String crearUsuario(UserModel userModel) {
        String msg;
        if (userRepo.existsById(userModel.getNif())){
            msg = "El usuario ya existe. Error al crear usuario";
//            throw new RuntimeException("Este usuario con NIF " + userModel.getNif() + " ya existe");
        } else {
            userRepo.save(userModel);
            msg = "El usuario se ha creado";
        }

        return msg;
    }

    //APARTADO 2
    public void modificarUsuario(UserModel userModel){
        try {
            UserModel newUser = listarUsuarios(userModel.getNif());
            newUser.setAnyoNacimiento(userModel.getAnyoNacimiento());
            newUser.setNombre(userModel.getNombre());
            newUser.setApellidos(userModel.getApellidos());
            newUser.setEmail(userModel.getEmail());
            newUser.setTelefono(userModel.getTelefono());
            newUser.setDireccion(userModel.getDireccion());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //ELIMINAR USUARIO POR NIF
    public void eliminarUsuario(String nif){

        userRepo.deleteById(nif);
    }


    //APARTADO 4
    public UserModel listarUsuarios(String nif){
        List<UserModel> lista = userRepo.findUserByNif(nif);
        return lista.get(0);
    }



}