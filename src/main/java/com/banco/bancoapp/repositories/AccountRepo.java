package com.banco.bancoapp.repositories;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends CrudRepository<AccountModel, Integer> {
    //List<AccountModel> listarCuentasPorUsuarios(UserModel userModel);

    List<AccountModel> findAccountModelsByUserModelsContaining(UserModel userModel);
}
