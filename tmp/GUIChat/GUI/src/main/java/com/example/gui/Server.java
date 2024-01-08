package com.example.gui;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
    private ServerSocket serverSocket;
    private static HashSet<ObjectOutputStream> outputStreams = new HashSet<>();

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Nueva conexión entrante");

                ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
                outputStreams.add(outStream);

                // Inicia un hilo para manejar la conexión del cliente
                new ClientHandler(socket, outStream).start();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar un mensaje a todos los clientes
    public static void sendMessageToAllClients(String message) {
        try {
            for (ObjectOutputStream clientStream : outputStreams) {
                clientStream.writeObject(message);
                clientStream.flush();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1408);
        Server server = new Server(serverSocket);
        server.startServer();
    }

    public static String SendResultsQuery(String query) {
        DatabaseSystem BD = new DatabaseSystem();
        return BD.DatabaseSystemStr(query);
    }
    private static class ClientHandler extends Thread {
        private Socket socket;
        private ObjectInputStream inStream;
        private ObjectOutputStream outStream;

        public ClientHandler(Socket socket, ObjectOutputStream outStream) {
            this.socket = socket;
            this.outStream = outStream;
        }

        public void run() {
            try {
                inStream = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    // Lee el mensaje enviado por el cliente
                    Object messageObject = inStream.readObject();

                    String message = (String) messageObject;
                    System.out.println("Mensaje recibido: " + message);

                    if (!message.endsWith(";")){
                        // Envía el mensaje a todos los clientes
                        ClientHandler1 clientHandler1 = new ClientHandler1(socket,message);

                        Thread thread = new Thread(clientHandler1);
                        thread.start();
                    }else{
                        String result = SendResultsQuery(message);
                        // Envia la respuesta a todos los clientes
                        for (ObjectOutputStream clientStream : outputStreams) {
                            try {
                                clientStream.writeObject(result);
                                clientStream.flush();
                            } catch (IOException e) {
                                System.out.println("error en enviar respuestas a los clientes?");
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                //e.printStackTrace();
            } finally {
                // Elimina la conexión del cliente cuando se cierra
                outputStreams.remove(outStream);
                try {
                    if (inStream != null) {
                        inStream.close();
                    }
                    if (outStream != null) {
                        outStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }
}