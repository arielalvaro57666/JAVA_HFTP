package client;

import client.clientconnection.ClientConnection;
import common.connection.Connection;
import server.serverconnection.ServerConnection;

import java.io.*;
import java.net.Socket;

public class Client {
    private final String host = "127.0.0.1";
    private final int port = 4000;

    public void connect() {
        try {
            Socket socket = new Socket(this.host, this.port);
            System.out.println("Succesfuly connected to HFTP");

            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream((socket.getOutputStream()));


            ClientConnection clientConnection = new ClientConnection(socket, in, out);
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

