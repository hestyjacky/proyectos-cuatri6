package com.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MenuInicialController extends encabezado implements Initializable {

    @FXML
    private ListView<String> ListView;
    @FXML
    private Label Label_user, Label_email;
    private String UsuarioEnSesion, Correo;
    private String ChatSeleccionado;


    public void setDatos(String UsuarioEnSesion, String Correo) {
        this.UsuarioEnSesion = UsuarioEnSesion;
        this.Correo = Correo;
        actualizarInterfaz();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (Socket socket = new Socket("localhost", 1408);
             ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("\nExiste conexión para Menu\n");

            String query = "select id from usuarios;";
            // Enviar la consulta al servidor remoto
            outStream.writeObject(query);

            // Recibir la respuesta del servidor
            String contenido = (String) inStream.readObject(); // recibo el resultado del query

            // Eliminar todos los caracteres de tabulación (\t)
            contenido = contenido.replace("\t", "");

            // Dividir la cadena por cada salto de línea (\n)
            String[] contenidoSplit = contenido.split("\n"); // lo hago un arreglo

            ObservableList<String> items = FXCollections.observableArrayList(
                    contenidoSplit // vacio el arreglo
            );
            ListView.setItems(items); // muestro la lista con el contenido del arreglo


            // Configurar el evento de selección en el ListView
            ListView.setOnMouseClicked(event -> {
                String ChatSeleccionado = ListView.getSelectionModel().getSelectedItem();
                if (ChatSeleccionado != null) {
                    System.out.println(ChatSeleccionado);
                    abrirVentanaDetalles(ChatSeleccionado);
                }
            });

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error con la conexión en el menú");
            e.printStackTrace();
        }
    }

    private void abrirVentanaDetalles(String selectedItem) {
        try {
            // Cargar la ventana de detalles desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatInd.fxml"));
            Stage stage = new Stage();

            this.ChatSeleccionado = selectedItem;
            ClientController clientController = new ClientController();
            clientController.setDatos(UsuarioEnSesion,this.ChatSeleccionado);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("chats");
            stage.setScene(new Scene(loader.load()));



            // Mostrar la ventana de detalles
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Error abriendo el chat...");
            e.printStackTrace();
        }
    }

    private void actualizarInterfaz() {
        Label_user.setText(UsuarioEnSesion);
        Label_email.setText(Correo);
    }

    public class VentanaDetalles {
        @FXML
        private Label labelDetalle;

        public void setDetalle(String detalle) {
            labelDetalle.setText(detalle);
        }
    }
}
