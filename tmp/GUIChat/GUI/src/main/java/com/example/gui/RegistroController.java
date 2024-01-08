package com.example.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroController {

    @FXML
    private Label Mensaje_Botones;
    @FXML
    private TextField RegistroUser;
    @FXML
    private TextField CorreoUser;
    @FXML
    private TextField ContrasenaUser;
    @FXML
    private Button Bonton_Minimizar;
    @FXML
    private Button Boton_Maximizar;
    @FXML
    public Text textUser;

    @FXML
    private void Salir_encabezado(ActionEvent event) {
        System.out.println("Salir...");
        System.out.println("Gracias por su preferencia!");
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Minimizar_encabezado() {
        Stage stage = (Stage) Bonton_Minimizar.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void MaximizarVentana() {
        Stage stage = (Stage) Boton_Maximizar.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    @FXML
    protected void Register_ButtonClick(ActionEvent event) {
        try (Socket socket = new Socket("localhost", 1408);
             ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Existe conexión para almacenar los datos");

            String userRegistro = RegistroUser.getText();
            String correo = CorreoUser.getText();
            String contrasena = ContrasenaUser.getText();

            if (correo.isBlank() || contrasena.isBlank() || userRegistro.isBlank()) {
                String user = "Ocupa llenar todos campos...";
                Mensaje_Botones.setText(user);
            } else {
                if (validarCorreo(correo)) {
                    // Consulta SQL segura utilizando consultas preparadas o métodos seguros
                    String query = "SELECT correo FROM usuarios WHERE correo = "+correo+";";
                    String query2 = "SELECT id FROM usuarios WHERE id = "+userRegistro+";";

                    // Enviar la consulta al servidor remoto
                    outStream.writeObject(query);

                    // Recibir la respuesta del servidor
                    String resultCorreo = (String) inStream.readObject();

                    outStream.writeObject(query2);

                    String resultId = (String) inStream.readObject();

                    if (!resultCorreo.contains(correo) && !resultId.contains(userRegistro)) {
                        // Consulta de inserción segura utilizando consultas preparadas o métodos seguros
                        String query3 = "insert into usuarios (id, correo, contraseña) values ("+userRegistro+", "+correo+", "+contrasena+");";
                        outStream.writeObject(query3);

                        Platform.runLater(() -> {
                            try {
                                InicioApp Inicio = new InicioApp();
                                Stage regScene = new Stage();
                                Inicio.start(regScene);

                                // Obtener la referencia de la ventana actual y cerrarla
                                Node source = (Node) event.getSource();
                                Stage stage = (Stage) source.getScene().getWindow();
                                stage.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        String user = "Utiliza otros datos...";
                        Mensaje_Botones.setText(user);
                    }
                } else {
                    String user = "Correo no válido...";
                    Mensaje_Botones.setText(user);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean validarCorreo(String correo) {
        String patronCorreo = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern pattern = Pattern.compile(patronCorreo);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}