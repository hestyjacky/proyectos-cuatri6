package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {
    @FXML
    public Text minimizeButton;

    @FXML
    private Label welcomeText;
    @FXML
    private TextField CorreoUser;
    @FXML
    private TextField ContrasenaUser;
    @FXML
    public Text textUser;

    @FXML
    private void minimizeWindow() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        // Lógica para la acción "Guardar"
        System.out.println("Salir...");

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void LogIn_ButtonClick(ActionEvent e) {
        String correo = CorreoUser.getText();
        String contrasena = ContrasenaUser.getText();

        if (correo.isBlank() || contrasena.isBlank()){
            String user = "Ocupa llenar ambos campos...";
            welcomeText.setText(user);
        }else{
            //String user = "Datos: " + correo + " " + contrasena + " !";
            String user = " Log-in exitoso !";
            welcomeText.setText(user);


            Node source = (Node) e.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            /*
            // Cargar la nueva ventana
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuInicial.fxml"));
                Parent root = loader.load();
                Stage nuevaVentana = new Stage();
                nuevaVentana.setScene(new Scene(root));

                // Cerrar la ventana actual
                Node source = (Node) e.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

                // Mostrar la nueva ventana
                nuevaVentana.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

             */
        }

    }

    @FXML
    protected void SignUp_ButtonClick(){
        welcomeText.setText("Abriendo página de registro...");
    }
}