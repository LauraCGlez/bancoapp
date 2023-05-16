package com.banco.bancoapp.repositories;

import com.banco.bancoapp.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<UserModel, String> {
   List<UserModel> findUserByNif(String nif);
}
