package common.connection;

import types.ReturnCodes;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {

    private final Socket socket;
    private BufferedInputStream in;
    private BufferedOutputStream out;

    public Connection(Socket socket, BufferedInputStream in_buff, BufferedOutputStream out_buff){
        this.socket = socket;
        this.in = in_buff;
        this.out = out_buff;
    }

    protected void readMessage(String message) {
        System.out.print("\n" + message + "\n");
    }

    /**
     * Blocks until a message is received in the stream
     * @return The String that the stream sent
     */
    protected String receiveStringMessage() throws IOException {
        //ReadAllByte Wont work since doesnt get to end of stream
        //ReadNBytes will work since it just read the ammount of bytes we say
        //TLV Approach

        int bytesAmount = this.in.read();
        byte[] buffer = this.in.readNBytes(bytesAmount);

        return new String(buffer, StandardCharsets.UTF_8);
    }

    // Mandarlos a una clase padre
    protected void sendStringMessage(String message) throws IOException {
        byte[] messageBytes = message.getBytes();
        this.out.write(messageBytes.length);
        this.out.write(messageBytes);
        this.out.flush();
    }


    protected void closeConnection() throws IOException {
        this.socket.close();
    }
}
