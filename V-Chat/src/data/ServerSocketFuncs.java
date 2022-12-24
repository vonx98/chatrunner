package data;

import data.data_classes.UserDetail;
import data.req_classes.ReqFuncs;
import data.req_classes.XmlParser;
import sql.Sqlite;
import sql.UserSettings;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerSocketFuncs {

    Map<String, Client> clients = new HashMap();

    ReqFuncs reqFuncs = new ReqFuncs();
    XmlParser xmlParser = new XmlParser();

    Sqlite sqlite = new Sqlite();
    String username = "";
    String password = "";
    UserSettings user = null;

    ArrayList<String> writerList = new ArrayList<>();

    public boolean listenClient(Socket socket) {

        Client client = null;


        try {

            ConnectionData connectionData = new ConnectionData(socket.getInetAddress().toString(), socket.getPort());

            printServerMessage(connectionData,client, " Client Connected !");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            while (true) {


                String data = dataInputStream.readUTF();
                System.out.println("inf:"+data);


                //printServerMessage(connectionData,client,data);


                if (data.equals("#3x1t"))
                {

                    printServerMessage(connectionData, client, "Connection is terminated...");
                    clients.remove(client.getUsername());
                    printServerMessage(null, client, "Connection is terminated...");
                    clients.remove(username, client);

                    sendToAll("setUserList");
                    ArrayList<UserDetail> userList = new ArrayList<>();
                    for (Client cl : clients.values())
                    {
                        System.out.println(cl.getUsername());
                        UserDetail ui = new UserDetail(cl.getUsername(),cl.getName(),cl.getSurname(),cl.getLevel(),cl.getImage());
                        userList.add(ui);
                    }

                    sendToAllImageList(userList);
                    printServerMessage(null,null,"Connection Terminaed !");
                    return false;

                }
                if (data.equals("getUserList"))
                {

                    ArrayList<UserDetail> userList = new ArrayList<>();
                    for (Client cl : clients.values())
                    {
                        System.out.println(cl.getUsername());
                        UserDetail ui = new UserDetail(cl.getUsername(),cl.getName(),cl.getSurname(),cl.getLevel(),cl.getImage());
                        userList.add(ui);
                    }


                    sendToAll("setUserList");


                    sendToAllImageList(userList);
                    System.out.println("sendimagelist");
                }
                else
                {
                    if (typeControl(data, "login")) {

                        username = xmlParser.getLoginData(data, "MessageData", "username");
                        password = xmlParser.getLoginData(data, "MessageData", "password");

                        Client clientControl = clients.get(username);

                        if (clientControl == null)
                        {

                            if (sqlite.loginControl(username, password))
                            {

                                printServerMessage(connectionData,client,"User("+username+") Found in Database");

                                user = sqlite.getSettings(username, password);

                                String message = xmlParser.createLoginSuccessXml("login", username, user.getNAME(), user.getSURNAME(), String.valueOf(user.getLEVEL()), "log1nSucce55", String.valueOf(user.getFIRSTLOGIN()));

                                //printServerMessage(connectionData,client,message);

                                client = new Client(user.getUSERNAME(), user.getNAME(), user.getSURNAME(), String.valueOf(user.getLEVEL()), user.getIMAGE(), socket);
                                client.objectOutputStream = objectOutputStream;
                                client.objectInputStream = objectInputStream;
                                client.dataOutputStream = dataOutputStream;
                                client.dataInputStream = dataInputStream;
                                client.inputStream = inputStream;
                                client.outputStream = outputStream;

                                client.getDataOutputStream().writeUTF(message);
                                client.getDataOutputStream().flush();


                                System.out.println("firstlogin:"+user.getFIRSTLOGIN());
                                if (user.getFIRSTLOGIN() == 0) {
                                    sendImage(user.getIMAGE(),outputStream);
                                    System.out.println("sendenLength:"+user.getIMAGE().length);
                                }

                                clients.put(username, client);

                                printServerMessage(connectionData, client, "has logined");
                                //sendToAll(xmlParser.createUserListXml("userList",clients));


                            }
                            else
                            {
                                String message = xmlParser.createLoginSuccessXml("login", "", "", "", "", "log1n3rr0r", "");
                                dataOutputStream.writeUTF(message);
                                dataOutputStream.flush();
                                printServerMessage(connectionData,client,message);

                            }
                        } else {
                            String message = xmlParser.createLoginSuccessXml("login", connectionData.getIpAddress()+"/"+connectionData.getPort(), "", "", "", "log1n3rr0rCtt", "");
                            dataOutputStream.writeUTF(message);
                            dataOutputStream.flush();
                            printServerMessage(connectionData,client,message);

                        }

                    }
                    else if (typeControl(data, "publicMessage")) {

                        printServerMessage(connectionData,client,messageInfoFormat(data,"MessageData"));
                        sendToAll(data);
                    }
                    else if (typeControl(data, "createProfile"))
                    {

                        if (user != null) {

                            String name = xmlParser.getData(data, "ClientData", "name");
                            String surname = xmlParser.getData(data, "ClientData", "surname");
                            String username = xmlParser.getData(data,"ClientData","username");

                            user.setUSERNAME(username);
                            user.setNAME(name);
                            user.setSURNAME(surname);
                            user.setLEVEL(1);
                            user.setFIRSTLOGIN(0);


                            client.setName(name);
                            client.setSurname(surname);


                            byte[] image = readImage(inputStream);
                            user.setIMAGE(image);


                            client.setImage(image);

                            clients.replace(username, client);


                            dataOutputStream.writeUTF("success");
                            dataOutputStream.flush();

                            sqlite.updateSettings(user);

                            printServerMessage(connectionData,client,"User data created");


                        }


                    }
                    else if (typeControl(data,"updateProfile")) {


                        String name = xmlParser.getData(data, "ClientData", "name");
                        String surname = xmlParser.getData(data, "ClientData", "surname");
                        String username = xmlParser.getData(data,"ClientData","username");

                        client.setName(name);
                        client.setSurname(surname);

                        System.out.println("updateProfile");
                        System.out.println("reading bytes");

                        byte[] image = readImage(inputStream);


                        System.out.println("read complate");
                        client.setImage(image);

                        clients.replace(username, client);

                        System.out.println(username);
                        System.out.println(name);
                        System.out.println(surname);

                        sqlite.updateUser(username,name,surname,image);
                        printServerMessage(connectionData,client,"User data Updated");



                    }
                    else if (typeControl(data, "getOnlineUsers"))
                    {







                    }
                    else if (typeControl(data,"writing")) {

                        String username = xmlParser.getData(data,"ClientData","writer");
                        if (!writerList.contains(username)) {
                            printServerMessage(connectionData,client,data);
                            writerList.add(username);
                            sendToAll(xmlParser.createWriterList("writerList",writerList));
                        }

                    }
                    else if (typeControl(data,"noWriting")) {

                        String username = xmlParser.getData(data,"ClientData","writer");

                        if (writerList.contains(username)) {

                            writerList.remove(username);
                            sendToAll(xmlParser.createWriterList("writerList",writerList));
                        }

                    }

                }


            }
        } catch (IOException e) {


            printServerMessage(null, client, "Connection is terminated...");



            clients.remove(client.getUsername());
            System.out.println("removed clients ("+client.getUsername()+")");

            ArrayList<UserDetail> userList = new ArrayList<>();


            for (Client cl : clients.values())
            {
                UserDetail ui = new UserDetail(cl.getUsername(),cl.getName(),cl.getSurname(),cl.getLevel(),cl.getImage());
                userList.add(ui);

            }

            sendToAll("setUserList");

            sendToAllImageList(userList);

            printServerMessage(null,null,"Connection Terminaed !");
            e.printStackTrace();
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }


    }

    private void sendPrivateMessage(String username, String data) throws IOException {

        Client client = clients.get(username);
        client.getObjectOutputStream().writeObject(data);
        client.getObjectOutputStream().flush();
    }

    private boolean typeControl(String data, String type) {
        if (data.contains("<type>" + type + "</type>")) {
            return true;
        } else {
            return false;
        }

    }

    public void sendToAll(String data) {
        try {
            for (Client client : clients.values()) {

                if (client.getSocket().isBound()) {
                    System.out.println(client.getUsername()+" sending "+data);

                    client.getDataOutputStream().writeUTF(data);
                    client.getDataOutputStream().flush();

                    System.out.println(client.getUsername()+" sended "+data);
                }

            }

        }
        catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void sendToAllImageList(ArrayList<UserDetail> data) {
        try {
            for (Client client : clients.values()) {
                client.getObjectOutputStream().writeObject(data);
                client.getObjectOutputStream().flush();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void printServerMessage(ConnectionData connectionData,Client client,String data) {

        String dateTime = reqFuncs.getDateTime();
        String message = "";

        if (client == null && connectionData != null) {
            String ip = connectionData.getIpAddress();
            String port = String.valueOf(connectionData.getPort());

            message = (dateTime + " : (SERVER) : [IP:"+ip +"/"+port+"] - " + data);
        }
        else if (client != null && connectionData == null)
        {
            String username = client.getUsername();
            String ip = String.valueOf(client.getSocket().getInetAddress());
            String port = String.valueOf(client.getSocket().getPort());

            message = (dateTime+" : (SERVER) [USER:"+username+"("+ip+"/"+port+")] - "+data);
        } else if (client != null && connectionData != null) {
            String username = client.getUsername();
            String ip = String.valueOf(client.getSocket().getInetAddress());
            String port = String.valueOf(client.getSocket().getPort());

            message = (dateTime+" : (SERVER) [USER:"+username+"("+ip+"/"+port+")] - "+data);
        } else {
            message = (dateTime+" : (SERVER) - "+data);
        }

        System.out.println(message);
    }


    private String messageInfoFormat(String data,String tag) {


        String type = xmlParser.getData(data,"MessageData","type");
        String sender = xmlParser.getData(data,"MessageData","username");
        String receiver = xmlParser.getData(data,"MessageData","receiver");
        String message = xmlParser.getData(data,"MessageData","message");



        String dataR = "";
        if (tag.equals("MessageData") && type.equals("publicMessage"))
        {
            dataR = "[TAG:"+tag+"] [TYPE:"+type+"] ["+"SENDER:"+sender+"] [RECEIVER:"+receiver+"] [MESSAGE:"+message+"]";
        }
        else if (tag.equals("MessageData") && type.equals("login"))
        {
            dataR = "[TAG:"+tag+"] [TYPE:"+type+"] [REQUESTIN-GUSER:"+sender+"] (Login request)";
        }
        else if (tag.equals("MessageData") && type.equals("getOnlineUsers"))
        {
            dataR = "[TAG:"+tag+"] [TYPE:"+type+"] ["+"SENDER:"+sender+"] [RECEIVER:"+sender+"] (requested active user list)";
        }
        else if (tag.equals("ClientData") && type.equals("writerList")){
            dataR = "[TAG:"+tag+"] [TYPE:"+type+"] (Writing user...)";

        }


        return dataR;
    }


    private byte[] readImage(InputStream inputStream) {

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
            throw new RuntimeException(e);
        }
    }


    public void sendImage(byte[] image,OutputStream outputStream) {

        try
        {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(image));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(img, "png", byteArrayOutputStream);
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
