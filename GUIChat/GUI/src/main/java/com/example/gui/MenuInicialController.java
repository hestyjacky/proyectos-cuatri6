package com.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuInicialController implements Initializable{

    // ----------------- TOOL BAR superior
    @FXML
    private void handleNuevo(ActionEvent event) {
        // Lógica para la acción "Nuevo"
        System.out.println("Nuevo");
    }

    @FXML
    private void handleAjustes(ActionEvent event) {
        // Lógica para la acción "Ajustes"
        System.out.println("Ajustes");
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
    private void handleMas(ActionEvent event) {
        // Lógica para la acción "Más"
        System.out.println("Más opciones");
    }

    // ----------------- DIVISION

    @FXML
    private Button BotonNuevo;

    @FXML
    private ToolBar Toolbar_Favoritos, Toolbar_Contactos, Toolbar_Grupos, Toolbar_ChatsRecientes;

    @FXML
    private ListView<String> ListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // acomodar toolbars
        Toolbar_Favoritos.setPrefWidth(210);
        //Toolbar_ChatsRecientes.setPrefHeight(40);
        //Toolbar_ChatsRecientes.setPrefWidth(305);

        ObservableList<String> items = FXCollections.observableArrayList(
          // lista de los chats recientes
                "Angela M" +
                        "\n >> adios",
                "omar P" +
                        "\n >> claro que no...",
                "hesty F" +
                        "\n >> brownies?",
                "Persona 1" +
                        "\n >> Gracias !",
                "Angela M" +
                        "\n >> adios",
                "omar P" +
                        "\n >> claro que no...",
                "hesty F" +
                        "\n >> brownies?",
                "Persona 1" +
                        "\n >> Gracias !",
                "Angela M" +
                        "\n >> adios",
                "omar P" +
                        "\n >> claro que no...",
                "hesty F" +
                        "\n >> brownies?",
                "Persona 1" +
                        "\n >> Gracias !",
                "Angela M" +
                        "\n >> adios",
                "omar P" +
                        "\n >> claro que no...",
                "hesty F" +
                        "\n >> brownies?",
                "Persona 1" +
                        "\n >> Gracias !"
        );

        ListView.setItems(items);

    }


    //----------------- PRIMER AREA

    @FXML
    private void BottonBuscar_Contactos(ActionEvent event) {
        // Lógica para la acción "Buscar" dentro del apartado de contactos
        System.out.println("Buscando en contactos...");
    }
    @FXML
    private void Botton_Primer_Contacto(ActionEvent event) {
        // Lógica para la acción
        System.out.println("Entrando al chat indv 1...");
    }
    @FXML
    private void Botton_DetallesContacto(ActionEvent event) {
        // Lógica para la acción
        System.out.println("Detalles chat...");
    }
    @FXML
    private void Botton_Segundo_Contacto(ActionEvent event) {
        // Lógica para la acción
        System.out.println("Entrando al chat indv 2...");
    }
    @FXML
    private void Botton_Tercer_Contacto(ActionEvent event) {
        // Lógica para la acción
        System.out.println("Entrando al chat indv 3...");
    }
    private ContextMenu contextoActual;
    @FXML
    public void handleClicDerecho(ContextMenuEvent event) {

        if (contextoActual != null) {
            contextoActual.hide();
        }

        contextoActual = new ContextMenu();

        MenuItem opcion1 = new MenuItem("Opción 1");
        opcion1.setOnAction(e -> handleOpcion1());

        MenuItem opcion2 = new MenuItem("Opción 2");
        opcion2.setOnAction(e -> handleOpcion2());

        // Agrega las opciones al menú contextual
        contextoActual.getItems().addAll(opcion1, opcion2);

        // Determina el botón sobre el que se hizo clic derecho
        Button boton = (Button) event.getSource();

        contextoActual.show(boton, event.getScreenX(), event.getScreenY());
    }
    private void handleOpcion1() {
        System.out.println("Se seleccionó Opción 1");
        contextoActual.hide();
    }
    private void handleOpcion2() {
        System.out.println("Se seleccionó Opción 2");
        contextoActual.hide();
    }


}
