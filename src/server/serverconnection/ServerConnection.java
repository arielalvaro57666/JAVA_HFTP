package server.serverconnection;

import common.connection.Connection;
import types.Commands;
import types.ReturnCodes;

import java.io.IOException;
import java.net.Socket;


public class ServerConnection extends Connection {
    // We will use dataInput and Output Streams for simple messages
    // And Buffered Streams for files
    // Client connection and server connection should inherate the connection class and extends their respective interfaces

    public ServerConnection(Socket socket) throws IOException {
        super(socket);
    }

    private void sendStatus(ReturnCodes e) {
        String status = e.getCode() + " " + e.getDescription();
        this.sendMessage(status);
    }

    public void menu(){

        while(true) {
            this.sendMenu();

            String request = this.receiveMessage();
            System.out.println("llego " + request);
            Commands requestCommand = Commands.returnCommand(request.toUpperCase());

            switch(requestCommand) {

                case LIST_DIRECTORY:
                    this.listDirectory();

                case DOWNLOAD_FILE:
                    this.upload();

                case UPLOAD_FILE:
                    this.download();

                case QUIT:
                    this.closeConnection();

                default:
                    this.sendStatus(ReturnCodes.INVALID_COMMAND);

            }


        }


    }

    // Mandarlos a una clase padre



    private void sendMenu() {
        for(Commands c : Commands.values()){

            this.sendMessage(c.getCommand());

        }
    }

    private void listDirectory() {
        this.sendMessage("Not implemented");
    }

    private void download() {
        this.sendMessage("Not implemented");
    }

    private void upload() {
        this.sendMessage("Not implemented");
    }

}
