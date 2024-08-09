package common.connection;

import java.io.*;
import java.net.Socket;

public class Connection {

    private final Socket socket;
    private InputStream in;
    private OutputStream out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    protected void readMessage(String message) {
        System.out.print("\n" + message + "\n");
    }

    /**
     * Blocks until a message is received in the stream
     * @return The String that the stream sent
     */
    protected String receiveMessage() {
        try {
            DataInputStream d_in = new DataInputStream(this.in);

            return  d_in.readUTF();

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }


    // Mandarlos a una clase padre
    protected void sendMessage(String message) {
        try {
            DataOutputStream d_out = new DataOutputStream(this.out);
            d_out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    protected void closeConnection(){
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
