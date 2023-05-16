package com.banco.bancoapp.repositories;

import com.banco.bancoapp.models.UserAccountIdModel;
import com.banco.bancoapp.models.UserAccountModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccounRepo extends CrudRepository<UserAccountModel, UserAccountIdModel> {

}
