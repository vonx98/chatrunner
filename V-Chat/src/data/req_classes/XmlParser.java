package data.req_classes;

import data.Client;
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
import java.util.Map;

public class XmlParser {



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
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


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
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


    }

    public String createMessageXml(String typeData,String usernameText,String nameText,String surnameText,String receiverData,String messageData,String timeData,byte[] imageByteData){
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
            username.appendChild(doc.createTextNode(usernameText));
            rootElement.appendChild(username);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(nameText));
            rootElement.appendChild(name);

            Element surname = doc.createElement("surname");
            surname.appendChild(doc.createTextNode(surnameText));
            rootElement.appendChild(surname);

            Element image = doc.createElement("imageBytes");
            image.appendChild(doc.createTextNode(Base64.getEncoder().encodeToString(imageByteData)));
            rootElement.appendChild(image);

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
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
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
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }
    public String createLoginSuccessXml(String typeData,String usernameText,String nameText,String surnameText,String levelText,String messageText,String firstLoginText) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // root element
        Element rootElement = doc.createElement("LoginSuccessData");
        doc.appendChild(rootElement);


        Element type = doc.createElement("type");
        type.appendChild(doc.createTextNode(typeData));
        rootElement.appendChild(type);

        Element message = doc.createElement("message");
        message.appendChild(doc.createTextNode(messageText));
        rootElement.appendChild(message);

        Element username = doc.createElement("username");
        username.appendChild(doc.createTextNode(usernameText));
        rootElement.appendChild(username);

        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(nameText));
        rootElement.appendChild(name);

        Element surname = doc.createElement("surname");
        surname.appendChild(doc.createTextNode(surnameText));
        rootElement.appendChild(surname);

        Element level = doc.createElement("level");
        level.appendChild(doc.createTextNode(levelText));
        rootElement.appendChild(level);

        Element firstLogin = doc.createElement("firstLogin");
        firstLogin.appendChild(doc.createTextNode(firstLoginText));
        rootElement.appendChild(firstLogin);




        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        transformer.transform(source, new StreamResult(writer));

        String output = writer.getBuffer().toString();
        return output;


    }
    public String createUserListXml(String typeData, Map<String,Client> clients) {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("ServerData");
            doc.appendChild(rootElement);

            // type element
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(typeData));
            rootElement.appendChild(type);

            // sender element
            int sayac = 0;
            for (Client client : clients.values())
            {
                Element element = doc.createElement("user");

                Attr attrId = doc.createAttribute("id");
                attrId.setValue(String.valueOf(sayac));
                element.setAttributeNode(attrId);


                Element usernameElement = doc.createElement("username");
                Element nameElement = doc.createElement("name");
                Element surnameElement = doc.createElement("surname");
                Element levelElement = doc.createElement("level");
                Element imageElemet = doc.createElement("imageBytes");

                usernameElement.appendChild(doc.createTextNode(client.getUsername()));
                nameElement.appendChild(doc.createTextNode(client.getName()));
                levelElement.appendChild(doc.createTextNode(client.getLevel()));
                surnameElement.appendChild(doc.createTextNode(client.getSurname()));
                imageElemet.appendChild(doc.createTextNode(Base64.getEncoder().encodeToString(client.getImage())));


                rootElement.appendChild(element);
                element.appendChild(nameElement);
                element.appendChild(surnameElement);
                element.appendChild(levelElement);
                element.appendChild(imageElemet);
                element.appendChild(usernameElement);

                sayac +=1;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));

            String output = writer.getBuffer().toString();
            return output;


        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    public String createWriterList(String typeData, ArrayList<String> writers) {

        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("ServerData");
            doc.appendChild(rootElement);

            // type element
            Element type = doc.createElement("type");
            type.appendChild(doc.createTextNode(typeData));
            rootElement.appendChild(type);




            for (String user : writers)
            {

                Element root = doc.createElement("user");

                Element usernameElement = doc.createElement("username");
                usernameElement.appendChild(doc.createTextNode(user));


                rootElement.appendChild(root);
                root.appendChild(usernameElement);

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));

            String output = writer.getBuffer().toString();
            return output;


        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }
}
