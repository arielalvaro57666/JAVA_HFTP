package common.connection;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {
    private final int BUFFER_SIZE = 8192; //5 MebiB
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




    protected void sendIntMessage(int b) throws IOException {
        this.out.write(b);
    }
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




    protected void upload(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        byte[] buffer;
        //send blocks amount
        System.out.println("el archivo a subir tiene " + file.length() + " bytes");
        int blocks =  (int) Math.ceil( (double) file.length()/BUFFER_SIZE) ;
        this.sendStringMessage(String.valueOf(blocks));

        System.out.println(file.length() + " / 8192 = " + blocks);
        //Send blocks of 8192 bytes
        for(int i = 0; i<blocks; i++) {
            buffer = bis.readNBytes(BUFFER_SIZE);
            this.out.write(buffer);
            this.out.flush();
        }
        bis.close();
        fis.close();
    }

    protected void download(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        byte[] buffer;

        //Read blocks amount
        int blocks = Integer.parseInt(this.receiveStringMessage());

        //Read blocks of 8192 bytes and write into file
        System.out.println("he recibido " +blocks + " bloques");
        for(int i = 0; i<blocks; i++) {
            buffer = this.in.readNBytes(BUFFER_SIZE);
            fos.write(buffer);
        }

        fos.close();

    }

    protected void closeConnection() throws IOException {
        this.socket.close();
    }
}
