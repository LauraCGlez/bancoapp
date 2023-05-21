package com.banco.bancoapp.repositories;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends CrudRepository<TransactionModel, Integer> {
    List<TransactionModel> findAllByCodigoOp(String codigo);

    List<TransactionModel> findByCuentaOrigenOrCuentaDestino(AccountModel origen, AccountModel destino);

}
