package com.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MenuInicialApp extends Application {
    @Override
    public void start(Stage stage) throws IOException { // main
        FXMLLoader fxmlLoader = new FXMLLoader(InicioApp.class.getResource("MenuInicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 668, 408);

        stage.initStyle(StageStyle.TRANSPARENT); //quita

        /*
        stage.setTitle("Chats");

         */
        stage.setScene(scene);
        stage.onCloseRequestProperty().setValue(event -> System.out.println("\nEnd - Chats!"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}