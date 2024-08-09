package client.clientconnection;

import common.connection.Connection;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection extends Connection {

    private final int COMMANDS_QUANTITY = 5;

    public ClientConnection(Socket socket) throws IOException {
        super(socket);
    }

    public void communicate() {

        while(true){
            //Read menu
            this.readMenu();

            Scanner scan = new Scanner(System.in);
            this.sendMessage(scan.nextLine());

            //Read response
            String response = this.receiveMessage();
            this.readMessage(response);

        }


    }


    private void readMenu() {
        String command;

        System.out.println("------------Commands------------");
        for(int i = 0; i < this.COMMANDS_QUANTITY ; i++) {
            command = this.receiveMessage();
            System.out.print(command+ " ");
        }

        System.out.println("\n-------------------------------\n");
    }

}
