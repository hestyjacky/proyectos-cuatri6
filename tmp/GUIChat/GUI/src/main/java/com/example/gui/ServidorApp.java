package com.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServidorApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Servidor1.fxml"));
        stage.setTitle("serv");
        stage.setScene(new Scene(root,478,396));
        stage.show();

                /*
        FXMLLoader fxmlLoader = new FXMLLoader(InicioApp.class.getResource("Servidor1.fxml"));
        System.out.println("hol");
        Scene scene = new Scene(fxmlLoader.load(), 478,396 );
        System.out.println("hola2");

        encabezado encabezado = new encabezado();
        encabezado.moverVentana(stage,scene);
        System.out.println("hola3");

        stage.setTitle("Servidor");
        stage.setScene(scene);
        stage.onCloseRequestProperty().setValue(event -> System.out.println("\nEnd - Server!"));
        System.out.println("hola4");

        stage.show();

                 */
    }

    public static void main(String[] args){
        launch(args);
    }
}