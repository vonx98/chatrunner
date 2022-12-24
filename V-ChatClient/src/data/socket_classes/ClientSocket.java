package data.socket_classes;


import data.data_classes.UserDetail;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ClientSocket {

    public Socket socket;


    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    private static InputStream inputStream;

    private static  OutputStream outputStream;

    public boolean connect() throws IOException, ClassNotFoundException {

        socket = new Socket("31.223.56.30",3333);

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        if (socket.isBound()) {

            System.out.println(true);
            return true;
        }
        else
        {
            return false;
        }


    }


    public Object getObject() throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }

    public void writeUTF(String message) throws IOException {

        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();

    }

    public void write(byte[] byteList) throws IOException {

        outputStream.write(byteList);
        outputStream.flush();

    }

    public String readUTF() throws IOException {

        return this.dataInputStream.readUTF();

    }

    public  ArrayList<UserDetail> readObject() throws IOException, ClassNotFoundException {

        ArrayList<UserDetail> userDetail = (ArrayList<UserDetail>) objectInputStream.readObject();
        return userDetail;
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeConnect() throws IOException {
        if (socket.isBound()) {
            dataOutputStream.writeUTF("#3x1t");
            socket.close();
        }
    }

    public byte[] readImage() {

        try
        {
            byte[] sizeAr = new byte[4];
            inputStream.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

            byte[] imageAr = new byte[size];
            inputStream.read(imageAr);

            return imageAr;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void sendImage(String imagePath) {

        try
        {
            BufferedImage img = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(img, "png", byteArrayOutputStream);
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
