package com.banco.bancoapp.repositories;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.UserModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends CrudRepository<AccountModel, Integer> {
    //List<AccountModel> listarCuentasPorUsuarios(UserModel userModel);

    List<AccountModel> findAccountModelsByUserModelsContaining(UserModel userModel);
}
