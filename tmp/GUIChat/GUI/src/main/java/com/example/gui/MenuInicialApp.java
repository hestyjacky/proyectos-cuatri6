package com.example.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MenuInicialApp extends Application {
    @Override
    public void start(Stage stage) throws IOException { // main
        FXMLLoader fxmlLoader = new FXMLLoader(InicioApp.class.getResource("MenuInicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1310,745);

        scene.getStylesheets().add(getClass().getResource("estilos_MenuInicial.css").toExternalForm());
        stage.setScene(scene);
        encabezado encabezado = new encabezado();
        encabezado.moverVentana(stage,scene);
        stage.setTitle("Menú");
        stage.onCloseRequestProperty().setValue(event -> System.out.println("\nEnd - Chats!"));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}