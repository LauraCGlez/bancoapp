package com.banco.bancoapp.repositories;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.UserAccountIdModel;
import com.banco.bancoapp.models.UserAccountModel;
import com.banco.bancoapp.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccounRepo extends CrudRepository<UserAccountModel, UserAccountIdModel> {
    List<UserAccounRepo> findByUserModelAndAccountModel(UserModel userModel, AccountModel accountModel);
}
