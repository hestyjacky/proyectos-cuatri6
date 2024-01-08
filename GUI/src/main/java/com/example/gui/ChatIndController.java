package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatIndController implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    @FXML
    private void handleSalir(ActionEvent event) {
        // Lógica para la acción "Guardar"
        System.out.println("Salir chat individual...");

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleMas(ActionEvent event) {
        // Lógica para la acción "Más"
        System.out.println("Más opciones");
    }

    @FXML
    private void pruebaMas(ActionEvent event) {
        // Lógica para la acción "Más"
        System.out.println("Más opciones");
    }
}
