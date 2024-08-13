package client.clientconnection;

import common.connection.Connection;
import types.Commands;

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
                String[] params = request.split(" ");
                this.sendStringMessage(params[0]);
                //String request = scan();
                Commands command = Commands.returnCommand(params[0]);



                switch (command) {

                    case LIST:
                        this.listDirectory();
                        break;
                    case DOWNLOAD:
                        this.saveFile(params[1], params[2]);
                        break;
                    case UPLOAD:
                        this.sendFile(params[1]);
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
                System.out.println("exploded "+ e.getMessage() );
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

    private void sendFile(String path) throws IOException {
        File file = new File(path);
        this.sendStringMessage(file.getName());
        //Upload specified file
        this.upload(file);
    }

    private void saveFile(String filename, String path) throws IOException {
        //download filename local_path
        this.sendStringMessage(filename);
        String completePath = path + "/" + filename;
        //Download received file
        this.download(completePath);

    }
}
