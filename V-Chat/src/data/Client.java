package data;

import java.io.*;
import java.net.Socket;

public class Client {


    private String username;
    private String name;
    private String surname;
    private String level;

    private byte[] image;

    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;

    public InputStream inputStream;

    public OutputStream outputStream;
    private Socket socket = null;

    public Client(String username,String name, String surname, String level,byte[] image, Socket socket) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.level = level;
        this.image = image;
        this.socket = socket;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getImage() {
        return image;
    }

    public String getLevel() {
        return level;
    }

    public Socket getSocket()
    {
        return this.socket;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
