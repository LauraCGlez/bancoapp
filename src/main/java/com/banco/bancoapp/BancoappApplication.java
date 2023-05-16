package com.banco.bancoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class BancoappApplication extends Application {
    private static Scene scene;
    private static Stage stage;

    private static ApplicationContext applicationContext;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.show();
        scene = new Scene(loadFXML("login"), 700, 700);
        stage.setMinHeight(700);
        stage.setMinWidth(700);
        stage.setTitle("Banco");
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BancoappApplication.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader.load();
    }

    public static void switchRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(BancoappApplication.class, args);
        launch();
    }
}
