package com.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RegistroApp extends Application {

    @Override
    public void start(Stage stage) throws IOException { // main
        FXMLLoader fxmlLoader = new FXMLLoader(InicioApp.class.getResource("Registro.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 668, 408);

        encabezado encabezado = new encabezado();
        encabezado.moverVentana(stage,scene);

        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());

        stage.setTitle("Registro de usuario");
        stage.setScene(scene);
        stage.onCloseRequestProperty().setValue(event -> System.out.println("\nEnd - Registro"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}