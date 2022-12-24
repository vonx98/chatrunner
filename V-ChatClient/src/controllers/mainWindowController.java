package controllers;

import com.jfoenix.controls.JFXTextField;
import data.data_classes.UserDetail;
import data.desing_classes.ChatCustomListCell;
import data.desing_classes.CustomListCell;
import data.req_classes.ReqFuncs;
import data.req_classes.WindowFuncs;
import data.req_classes.XmlParser;
import data.socket_classes.ClientSocket;
import data.desing_classes.TextData;
import data.socket_classes.SUserData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class mainWindowController implements Initializable {


    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label level_label;

    @FXML
    private ListView<SUserData> user_listeview;

    @FXML
    private ListView<TextData> chat_listView;

    @FXML
    private Label username_label;

    @FXML
    private Label userCounter_label;

    @FXML
    private Label info_label;

    @FXML
    private Label name_label;

    @FXML
    private JFXTextField chat_textField;

   

    @FXML
    private Circle image_circle;

    @FXML
    private VBox chatVbox;

    WindowFuncs windowFuncs = new WindowFuncs();
    static ReqFuncs reqFuncs = new ReqFuncs();

    XmlParser xmlParser = new XmlParser();

    static DataInputStream dataInputStream ;
    static DataOutputStream dataOutputStream ;
    static ObjectInputStream objectInputStream ;
    static ObjectOutputStream objectOutputStream ;

    public SUserData userData = null;

    public ClientSocket clientSocket = null;



    @FXML
    void sendBtn_onAction() {

    }


    @FXML
    void test() {
        if (!chat_textField.getText().isEmpty()) {
            Platform.runLater(()->{

                try
                {
                    String time = reqFuncs.getTime();

                    clientSocket.writeUTF(xmlParser.createMessageXml(userData,"publicMessage","all",chat_textField.getText(),time));

                    TextData td = new TextData(userData.getUsername(),userData.getName()+" "+userData.getSurname(), chat_textField.getText(),time,reqFuncs.byteToImage(userImages.get(userData.getUsername())),false);
                    chat_listView.getItems().add(td);
                    chat_listView.scrollTo(chat_listView.getItems().size() - 1);
                    chat_listView.refresh();

                    chat_textField.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    void hideBtn_onAction() {
        Platform.runLater(() -> level_label.getScene().getWindow().hide());
    }
    @FXML
    void exitBtn_onAction() {
        try
        {
            clientSocket.closeConnect();
            System.exit(1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void settingsBtn_onAction() {

        Platform.runLater(()->{
            try
            {
                windowFuncs.createWindow("fxml/profileWindow.fxml","Chatrunner Beta v1.0",false,false);
                profileWindowController controller = (profileWindowController) windowFuncs.getCurrentController();
                controller.clientSocket = this.clientSocket;
                controller.update = true;
                controller.username = userData.getUsername();
                controller.image = reqFuncs.byteToImage(userData.getImage());
                controller.name = userData.getName();
                controller.surname = userData.getSurname();

                Stage stg = windowFuncs.getStage();
                stg.showAndWait();

                clientSocket.writeUTF("getUserList");


            }
            catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    ArrayList<String> writerList = new ArrayList<>();
    Map<String,byte[]> userImages = new HashMap<>();


    boolean writing = false;

    double xOffset = 0;
    double yOffset = 0;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(SystemTray.isSupported())
        {
            Platform.setImplicitExit(false);
            final PopupMenu popup = new PopupMenu();

            Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/1.png"));

            final TrayIcon trayIcon = new TrayIcon(image);
            trayIcon.setImageAutoSize(true);
            final SystemTray tray = SystemTray.getSystemTray();


            MenuItem show = new MenuItem("Göster");
            MenuItem exit = new MenuItem("Çık");

            popup.add(show);
            popup.addSeparator();
            popup.add(exit);

            trayIcon.setPopupMenu(popup);

            trayIcon.addActionListener(e -> {
                Platform.runLater(() -> {
                    Stage curStage = (Stage) level_label.getScene().getWindow();
                    curStage.show();
                });
            });

            show.addActionListener(e -> {
                Platform.runLater(() -> {
                    Stage curStage = (Stage) level_label.getScene().getWindow();
                    curStage.show();
                });
            });

            exit.addActionListener(e -> {
                try
                {
                    clientSocket.closeConnect();
                    System.exit(1);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            });



            try
            {
                tray.add(trayIcon);
            }
            catch (AWTException e)
            {
                e.printStackTrace();
                System.out.println("tryicon error");
            }
        }

        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = info_label.getScene().getWindow().getX() - event.getScreenX();
                yOffset = info_label.getScene().getWindow().getY() - event.getScreenY();
            }
        });

        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                info_label.getScene().getWindow().setX(event.getScreenX() + xOffset);
                info_label.getScene().getWindow().setY(event.getScreenY() + yOffset);
            }
        });


        chat_listView.setCellFactory(new Callback<ListView<TextData>, ListCell<TextData>>() {
            @Override
            public ListCell<TextData> call(ListView<TextData> listView) {
                return new ChatCustomListCell();
            }
        });

        user_listeview.setCellFactory(new Callback<ListView<SUserData>, ListCell<SUserData>>() {
            @Override
            public ListCell<SUserData> call(ListView<SUserData> listView) {
                return new CustomListCell();
            }
        });

        chat_textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,String newValue) {
                if (newValue.length() > 0) {

                    if (!writing)
                    {
                        try
                        {
                            String message = xmlParser.createWritingXml("writing",userData.getUsername());
                            System.out.println(message);
                            clientSocket.writeUTF(message);
                            writing = true;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                else if (newValue.length() == 0) {
                    try
                    {
                        String message = xmlParser.createWritingXml("noWriting",userData.getUsername());
                        clientSocket.writeUTF(message);
                        writing = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        chat_textField.setOnKeyReleased(event -> {
            String time = reqFuncs.getTime();
            if (event.getCode() == KeyCode.ENTER){
                Platform.runLater(()->{
                    try
                    {

                        clientSocket.writeUTF(xmlParser.createMessageXml(userData,"publicMessage","all",chat_textField.getText(),time));
                        chat_listView.getItems().add(new TextData(userData.getUsername(),userData.getName()+" "+userData.getSurname(), chat_textField.getText(),time,reqFuncs.byteToImage(userImages.get(userData.getUsername())),false));
                        chat_listView.scrollTo(chat_listView.getItems().size() - 1);
                        chat_listView.refresh();
                        chat_textField.clear();
                        writing = false;
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
        });

        Platform.runLater(()->{


                new Thread(()->{

                    try
                    {

                        System.out.println(userData.getImage().length);
                        image_circle.setStroke(Color.BLUEVIOLET);
                        image_circle.setFill(new ImagePattern(reqFuncs.byteToImage(userData.getImage())));
                        image_circle.setEffect(new DropShadow(+5d,0D,+1D, Color.BLUE));

                        username_label.setText(userData.getUsername());
                        name_label.setText(userData.getName()+" "+userData.getSurname());
                        level_label.setText("Seviye "+userData.getLevel());

                        clientSocket.writeUTF("getUserList");

                        while (true)
                        {

                            System.out.println("listening...");
                            String receiveData = clientSocket.readUTF();
                            System.out.println("rec:"+receiveData);

                            if (receiveData.equals("setUserList")) {
                                try
                                {

                                    System.out.println("userlist capture");
                                    ArrayList<UserDetail> userList = clientSocket.readObject();
                                    System.out.println("readData");

                                    ObservableList<SUserData> ulist = reqFuncs.getUserList(userList);


                                    Platform.runLater(()->{
                                        user_listeview.setItems(ulist);
                                        userImages = reqFuncs.getImages();
                                        user_listeview.refresh();
                                        userCounter_label.setText(userList.size() + " Kişi Şu Anda Aktif");
                                    });

                                    for (SUserData data : user_listeview.getItems())
                                    {
                                        if (data.getUsername().equals(userData.getUsername()))
                                        {
                                            Platform.runLater(()->{
                                                try
                                                {
                                                    System.out.println(data.getUsername());
                                                    name_label.setText(data.getName()+" "+data.getSurname());
                                                    image_circle.setFill(new ImagePattern(reqFuncs.byteToImage(data.getImage())));
                                                    level_label.setText("Seviye "+data.getLevel());
                                                }
                                                catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                            });
                                            break;
                                        }
                                    }
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                                catch (ClassNotFoundException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            if (reqFuncs.typeControl(receiveData,"publicMessage")){


                                String username = xmlParser.getData(receiveData,"MessageData","username");
                                String name = xmlParser.getData(receiveData,"MessageData","name");
                                String surname = xmlParser.getData(receiveData,"MessageData","surname");
                                String message = xmlParser.getData(receiveData,"MessageData","message");


                                String time = xmlParser.getData(receiveData,"MessageData","time");


                                System.out.println("systemUser:"+userData.getUsername());
                                System.out.println("meesageUser:"+username);
                                if (!userData.getUsername().equals(username))
                                {
                                           Platform.runLater(()->{
                                               try
                                               {

                                                   chat_listView.getItems().add(new TextData(username,name+" "+surname, message,time,reqFuncs.byteToImage(userImages.get(username)),true));
                                                   chat_listView.scrollTo(chat_listView.getItems().size() - 1);
                                                   chat_listView.refresh();

                                               } catch (IOException e) {
                                                   e.printStackTrace();
                                               }
                                           });
                                       }
                                if (writerList.isEmpty()) {
                                    writerList.remove(username);
                                    String text = "";
                                    for (String writer : writerList) {
                                        if (!writer.equals(userData.getUsername()))
                                        {
                                            text += writer + " yazıyor... ";
                                        }

                                    }
                                    String finalText = text;
                                    System.out.println(finalText);
                                    Platform.runLater(()->{
                                        info_label.setText(finalText);
                                    });
                                }



                            }
                            if (reqFuncs.typeControl(receiveData,"writerList")) {
                                System.out.println("data:"+receiveData);
                                writerList = xmlParser.getWriterList(receiveData);
                                String text = "";
                                for (String writer : writerList) {
                                    if (!writer.equals(userData.getUsername()))
                                    {
                                        text += writer + " yazıyor... ";
                                    }

                                }
                                String finalText = text;
                                System.out.println(finalText);
                                Platform.runLater(()->{
                                    info_label.setText(finalText);
                                });
                            }

                            chat_listView.refresh();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }

                }).start();


        });


    }





}
