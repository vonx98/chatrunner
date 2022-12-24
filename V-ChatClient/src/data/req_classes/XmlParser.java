package data.req_classes;

import data.socket_classes.SUserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class XmlParser {


    Map<String,byte[]> userImages = new HashMap<>();

    public String getData(String data,String mainTag,String elementTag){

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(data);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
            Document document = builder.parse(byteArrayInputStream);

            NodeList list = document.getElementsByTagName(mainTag);
            String text = "";
            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    text = element.getElementsByTagName(elementTag).item(0).getTextContent();
                    break;
                }


            }
            return text;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex2) {
            ex2.printStackTrace();
        }


        return data;
    }

    public String getLoginData(String data,String mainTag,String elementTag){

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(data);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
            Document document = builder.parse(byteArrayInputStream);

            NodeList list = document.getElementsByTagName(mainTag);
            String text = "";
            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    text = element.getElementsByTagName(elementTag).item(0).getTextContent();
                    break;
                }


            }
            return text;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        return data;
    }
    public String createMessageXml(SUserData userData,String typeData,String receiverData,String messageData,String timeData){
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("MessageData");
            doc.appendChild(rootElement);

            // type element
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(typeData));
            rootElement.appendChild(type);

            // sender element
            Element username = doc.createElement("username");
            username.appendChild(doc.createTextNode(userData.getUsername()));
            rootElement.appendChild(username);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(userData.getName()));
            rootElement.appendChild(name);

            Element surname = doc.createElement("surname");
            surname.appendChild(doc.createTextNode(userData.getSurname()));
            rootElement.appendChild(surname);

            // receiver element
            Element receiver = doc.createElement("receiver");
            receiver.appendChild(doc.createTextNode(receiverData));
            rootElement.appendChild(receiver);

            // receiver element
            Element time = doc.createElement("time");
            time.appendChild(doc.createTextNode(timeData));
            rootElement.appendChild(time);

            // receiver element
            Element message = doc.createElement("message");
            message.appendChild(doc.createTextNode(messageData));
            rootElement.appendChild(message);



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));

            String output = writer.getBuffer().toString();
            return output;


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return typeData;
    }
    public String createLoginXml(String typeData,String usernameData,String passwordData,String loginDateData){
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("MessageData");
            doc.appendChild(rootElement);

            // type element
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(typeData));
            rootElement.appendChild(type);

            // sender element
            Element username = doc.createElement("username");
            username.appendChild(doc.createTextNode(usernameData));
            rootElement.appendChild(username);

            // receiver element
            Element password = doc.createElement("password");
            password.appendChild(doc.createTextNode(passwordData));
            rootElement.appendChild(password);

            // receiver element
            Element loginDate = doc.createElement("loginDate");
            loginDate.appendChild(doc.createTextNode(loginDateData));
            rootElement.appendChild(loginDate);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));

            String output = writer.getBuffer().toString();
            return output;


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return typeData;
    }

    public String createWritingXml(String typeData,String writerText){
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("ClientData");
            doc.appendChild(rootElement);

            // type element
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(typeData));
            rootElement.appendChild(type);

            // sender element
            Element wrtr = doc.createElement("writer");
            wrtr.appendChild(doc.createTextNode(writerText));
            rootElement.appendChild(wrtr);



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));

            String output = writer.getBuffer().toString();
            return output;


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return typeData;
    }

    public String createProfileXml(String typeData,String usernameText,String nameText,String surnameText){
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("ClientData");
            doc.appendChild(rootElement);

            // type element
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(typeData));
            rootElement.appendChild(type);

            Element username = doc.createElement("username");
            username.appendChild(doc.createTextNode(usernameText));
            rootElement.appendChild(username);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(nameText));
            rootElement.appendChild(name);


            Element surname = doc.createElement("surname");
            surname.appendChild(doc.createTextNode(surnameText));
            rootElement.appendChild(surname);



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));

            String output = writer.getBuffer().toString();
            return output;


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return typeData;
    }
    public ObservableList<SUserData> getUserListXml(String data) throws ParserConfigurationException, IOException, SAXException {

        ObservableList<SUserData> userList = FXCollections.observableArrayList();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append(data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
        Document document = builder.parse(byteArrayInputStream);

        NodeList list = document.getElementsByTagName("ServerData");

        for (int temp = 0; temp < list.getLength(); temp++) {

            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                NodeList users = element.getElementsByTagName("user");

                for (int temp2 = 0 ; temp2 < users.getLength() ; temp2++) {
                    Node userNode = users.item(temp2);
                    if (userNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element userElement = (Element) userNode;
                        String username = userElement.getElementsByTagName("username").item(0).getTextContent();
                        String name = userElement.getElementsByTagName("name").item(0).getTextContent();
                        String surname = userElement.getElementsByTagName("surname").item(0).getTextContent();
                        String level = userElement.getElementsByTagName("level").item(0).getTextContent();
                        String imageByteString = userElement.getElementsByTagName("imageBytes").item(0).getTextContent();

                        SUserData userData = new SUserData(Base64.getDecoder().decode(imageByteString),username,name,surname,Integer.parseInt(level));

                        userImages.put(username,Base64.getDecoder().decode(imageByteString));

                        userList.add(userData);
                    }
                }


            }


        }

        return userList;

    }

    public ArrayList<String> getWriterList(String data) throws ParserConfigurationException, IOException, SAXException {

        ArrayList<String> writerList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append(data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
        Document document = builder.parse(byteArrayInputStream);

        NodeList list = document.getElementsByTagName("ServerData");

        for (int temp = 0; temp < list.getLength(); temp++) {

            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                NodeList users = element.getElementsByTagName("user");

                for (int temp2 = 0 ; temp2 < users.getLength() ; temp2++) {
                    Node userNode = users.item(temp2);
                    if (userNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element userElement = (Element) userNode;
                        String username = userElement.getElementsByTagName("username").item(0).getTextContent();
                        writerList.add(username);
                    }
                }


            }


        }
        return writerList;
    }



    public Map<String, byte[]> getUserImages() {
        return userImages;
    }

    public String updateProfile(String typeData,String usernameText,String nameText,String surnameText){
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("ClientData");
            doc.appendChild(rootElement);

            // type element
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(typeData));
            rootElement.appendChild(type);

            // type element
            Element username = doc.createElement("username");
            username.appendChild(doc.createTextNode(usernameText));
            rootElement.appendChild(username);


            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(nameText));
            rootElement.appendChild(name);


            Element surname = doc.createElement("surname");
            surname.appendChild(doc.createTextNode(surnameText));
            rootElement.appendChild(surname);



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));

            String output = writer.getBuffer().toString();
            return output;


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return typeData;
    }
}
