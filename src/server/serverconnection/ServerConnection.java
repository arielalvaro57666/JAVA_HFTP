package server.serverconnection;

import common.connection.Connection;
import types.Commands;
import types.ReturnCodes;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class ServerConnection extends Connection {
    // We will use dataInput and Output Streams for simple messages
    // And Buffered Streams for files

    public final String directory = "/home/gusmoon/Documents/hftp";


    public ServerConnection(Socket socket, BufferedInputStream in, BufferedOutputStream out) {
        super(socket, in, out);
    }

    private String scan() {
        Scanner scan = new Scanner(System.in);

        return scan.nextLine();
    }

    public void menu(){

        while(true) {
            try {
                this.sendMenu();
                String request = this.receiveStringMessage();

                Commands requestCommand = Commands.returnCommand(request.toUpperCase());
                System.out.println("Receive "+ requestCommand);
                switch(requestCommand) {

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
                //this.sendStatus(ReturnCodes.INTERNAL_ERROR);
            }




        }


    }

    // Mandarlos a una clase padre



    private void sendMenu() throws IOException {

        for(Commands c : Commands.values()){

            this.sendStringMessage(c.getCommand());

        }
    }

    private void listDirectory() throws Exception{
        File directory = new File(this.directory);
        String[] files = directory.list();



        if(files == null || files.length == 0 ) {
            //Could it be an exception
            this.sendStringMessage(ReturnCodes.EMPTY_DIRECTORY.getDescription());
            return;
        }

        this.sendStringMessage(String.valueOf(files.length));
        for(String file: files) {
            this.sendStringMessage(file);
        }

    }

/*    private void download() {
        this.sendStringMessage("Not implemented");
    }

    private void upload() {
        this.sendStringMessage("Not implemented");
    }*/

}
