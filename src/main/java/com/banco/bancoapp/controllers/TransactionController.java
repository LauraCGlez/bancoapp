package com.banco.bancoapp.controllers;

import com.banco.bancoapp.models.AccountModel;
import com.banco.bancoapp.models.TipoOpModel;
import com.banco.bancoapp.models.TransactionModel;
import com.banco.bancoapp.models.UserModel;
import com.banco.bancoapp.services.AccountService;
import com.banco.bancoapp.services.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;
    @FXML
    private TableColumn<TransactionModel, Integer> idColumn;
    @FXML
    private TableColumn<TransactionModel, String> tipoOpColumn;
    @FXML
    private TableColumn<TransactionModel, Double> cantidadColumn;
    @FXML
    private TableColumn<TransactionModel, String> fechaOpColumn;


    @FXML
    public void initialize() {
        // Configurar los valores del ComboBox

        // Configurar las columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoOpColumn.setCellValueFactory(new PropertyValueFactory<>("tipoOp"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fechaOpColumn.setCellValueFactory(new PropertyValueFactory<>("fechaOp"));
    }

}