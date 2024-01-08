package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class encabezado {
    @FXML
    private Button Bonton_Minimizar;

    @FXML
    private void Minimizar_encabezado() {
        // Obtener la referencia de la ventana actual
        Stage stage = (Stage) Bonton_Minimizar.getScene().getWindow();

        // Minimizar la ventana
        stage.setIconified(true);
    }

    @FXML
    private Button Boton_Maximizar;

    @FXML
    private void MaximizarVentana() {
        // Obtener la referencia de la ventana actual
        Stage stage = (Stage) Boton_Maximizar.getScene().getWindow();

        // Verificar si la ventana está maximizada y actuar en consecuencia
        if (stage.isMaximized()) {
            stage.setMaximized(false); // Desmaximizar la ventana
        } else {
            stage.setMaximized(true);  // Maximizar la ventana
        }
    }

    @FXML
    private void Salir_encabezado(ActionEvent event) {
        // Lógica para la acción "Guardar"
        System.out.println("Salir...");
        System.out.println("Gracias por su preferencia!");
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private double xOffset = 0;
    private double yOffset = 0;
    public void moverVentana(Stage stage, Scene scene){
        stage.initStyle(StageStyle.TRANSPARENT); // Barra de botones por default

        // Manejar eventos del ratón para permitir que la ventana se mueva
        scene.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
