package com.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException { // main
        FXMLLoader fxmlLoader = new FXMLLoader(InicioApp.class.getResource("ChatInd.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 478, 396);

        stage.setTitle("Chats Individual 1");
        stage.setScene(scene);
        stage.onCloseRequestProperty().setValue(event -> System.out.println("\nEnd - Chats individual 1!"));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
