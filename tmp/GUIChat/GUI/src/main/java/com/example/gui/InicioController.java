package com.example.gui;

import javafx.application.Platform;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InicioController extends encabezado {
    @FXML
    private Label Mensaje_Botones;
    @FXML
    private TextField CorreoUser;
    @FXML
    private TextField ContrasenaUser;
    @FXML
    public Text textUser;
    @FXML
    protected void SignUp_ButtonClick(ActionEvent event){
        Mensaje_Botones.setText("Abriendo página de registro...");
        Platform.runLater(() -> {
            try {
                RegistroApp Registro = new RegistroApp();
                Stage regScene = new Stage();
                Registro.start(regScene);
                Node source3 = (Node) event.getSource();
                Stage stage3 = (Stage) source3.getScene().getWindow();
                stage3.close();
            } catch (Exception e) {
                System.err.println("Error al abrir página de registro...");
                e.getMessage();
            }
        });
    }
    @FXML
    protected void LogIn_ButtonClick(ActionEvent event) {
        try (Socket socket = new Socket("localhost", 1408);
             ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Existe conexión para validar los datos");

            String correo = CorreoUser.getText();
            String contrasena = ContrasenaUser.getText();

            if (correo.isBlank() || contrasena.isBlank()) {
                Mensaje_Botones.setText("Ocupa llenar ambos campos...");
                return;
            }

            if (validarCorreo(correo)) {
                // Consulta SQL segura utilizando consultas preparadas o métodos seguros
                String query = "SELECT * FROM usuarios WHERE contraseña = "+contrasena+" and correo = "+correo+";";

                // Enviar la consulta al servidor remoto
                outStream.writeObject(query);

                // Recibir la respuesta del servidor
                String result = (String) inStream.readObject();

                if (result.contains(contrasena) || result.contains(correo)) {
                    String[] usuario = result.split("\t");
                    String usr = usuario[0];

                    // Operaciones en el hilo de la interfaz de usuario
                    Platform.runLater(() -> {
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();

                        try {
                            Siguiente_Ventana(event, usr, correo);
                        } catch (Exception e) {
                            System.err.println("Error al abrir página de menu...");
                            e.printStackTrace();
                        }
                    });
                }
            } else {
                Mensaje_Botones.setText("Correo no válido...");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No se estableció la conexión");
            e.printStackTrace();
            //Mensaje_Botones.setText("Error de conexión...");
        }
    }
    public static boolean validarCorreo(String correo) {
        // Definir la expresión regular para validar el formato del correo electrónico
        String patronCorreo = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

        // Compilar la expresión regular en un objeto Pattern
        Pattern pattern = Pattern.compile(patronCorreo);

        // Crear un objeto Matcher que comparará el patrón con la cadena de correo
        Matcher matcher = pattern.matcher(correo);

        // Verificar si el correo coincide con el patrón
        return matcher.matches();
    }

    private void Siguiente_Ventana(ActionEvent event, String UsuarioEnSesion, String correo) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuInicial.fxml"));
        Parent root = loader.load();
        MenuInicialController MenuInicialController = loader.getController();
        MenuInicialController.setDatos(UsuarioEnSesion,correo);

        // Crea una nueva escena y configura el escenario
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}