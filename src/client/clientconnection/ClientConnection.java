package client.clientconnection;

import common.connection.Connection;
import types.Commands;
import types.ReturnCodes;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection extends Connection {

    private final int COMMANDS_QUANTITY = 5;

    public ClientConnection(Socket socket, BufferedInputStream in, BufferedOutputStream out) {
        super(socket, in, out);
    }

    private String scan() {
        Scanner scan = new Scanner(System.in);

        return scan.nextLine();
    }

    public void communicate() {


        while(true){
            try {
                //Read menu
                this.readMenu();

                //Send command
                String request = scan();
                this.sendStringMessage(request);
                //String request = scan();
                Commands command = Commands.returnCommand(request);

                switch (command) {

                    case LIST:
                        this.listDirectory();
                        break;

                    case DOWNLOAD:
                        //this.upload();
                        break;
                    case UPLOAD:
                        //this.download();
                        break;
                    case QUIT:
                        this.closeConnection();
                        break;

                    default:
                        //this.sendStatus(ReturnCodes.INVALID_COMMAND);
                        break;
                }

            }
            catch (Exception e) {
                System.out.println("------------Commands------------");
            }


        }





    }


    private void readMenu() throws IOException{


        String command;

        System.out.println("------------Commands------------");
        for(int i = 0; i < this.COMMANDS_QUANTITY ; i++) {
            command = this.receiveStringMessage();
            System.out.print(command+ " ");
        }

        System.out.println("\n-------------------------------\n");
    }


    void listDirectory() throws IOException{
        int fileAmount = Integer.parseInt(this.receiveStringMessage());
        System.out.println("FILES: " + fileAmount);
        for(int i = 0; i < fileAmount; i++) {
            String file = this.receiveStringMessage();
            System.out.println(file);
        }

    }
}
