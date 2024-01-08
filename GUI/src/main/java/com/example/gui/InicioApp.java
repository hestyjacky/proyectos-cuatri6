package com.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class InicioApp extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException { // main
        FXMLLoader fxmlLoader = new FXMLLoader(InicioApp.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 668, 408);

        stage.initStyle(StageStyle.UNDECORATED);

        // Manejar eventos del ratón para permitir que la ventana se mueva
        scene.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());

        stage.setTitle("Inicio de sesión");
        stage.setScene(scene);
        stage.onCloseRequestProperty().setValue(event -> System.out.println("\nEnd - Inicio!"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}