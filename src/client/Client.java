package client;

import client.clientconnection.ClientConnection;
import common.connection.Connection;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final String host = "127.0.0.1";
    private final int port = 4000;

    public void connect() {
        try {
            Socket socket = new Socket(this.host, this.port);
            System.out.println("Succesfuly connected to HFTP");
            ClientConnection clientConnection = new ClientConnection(socket);
            clientConnection.communicate();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args){
        Client cl = new Client();

        cl.connect();
    }

}

