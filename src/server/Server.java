package server;

import server.serverconnection.ServerConnection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final String host;
    private final int port;

    private ServerSocket server_socket;
    private Socket client_socket;


    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //Listen and derivate client
    public void serve() {

        try{
            this.server_socket = new ServerSocket(this.port);
            System.out.println("Server Initialized");
            // Wait for connection
            while(true) {
                this.client_socket = this.server_socket.accept();

                BufferedInputStream in = new BufferedInputStream(this.client_socket.getInputStream());
                BufferedOutputStream out = new BufferedOutputStream((this.client_socket.getOutputStream()));

                ServerConnection serverConnection = new ServerConnection(this.client_socket, in, out);
                serverConnection.menu();
            }


        }catch (IOException e){
            System.out.println("KABOOM1 " + e.getMessage());
        }





    }


    public static void main(String[] args) {
        Server sv = new Server("127.0.0.1", 4000);

        sv.serve();

    }

}
