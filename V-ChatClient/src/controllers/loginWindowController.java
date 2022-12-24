package controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import data.req_classes.ReqFuncs;
import data.req_classes.WindowFuncs;
import data.req_classes.XmlParser;
import data.socket_classes.ClientSocket;
import data.socket_classes.SUserData;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sql.Sqlite;
import sql.UserSettings;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;

public class loginWindowController implements Initializable {


    @FXML
    private Label info_label;

    @FXML
    private JFXPasswordField password_passField;

    @FXML
    private JFXCheckBox rememberMe_checkBox;

    @FXML
    private JFXTextField username_textField;

    @FXML
    void loginBtn_onAction() {


        String username = username_textField.getText();
        String password = password_passField.getText();
        String loginTime = reqFuncs.getDateTime();

        Platform.runLater(()->{
            info_label.setText("");

            new Thread(()->{
                try
                {


                    clientSocket.writeUTF(xmlParser.createLoginXml(
                            "login",
                            username,
                            password,
                            loginTime));

                    String receivedData = clientSocket.readUTF();
                    String message = xmlParser.getData(receivedData,"LoginSuccessData","message");
                    System.out.println("recv:"+receivedData);

                    if (message.equals("log1nSucce55"))
                    {



                        String firstlogin = xmlParser.getData(receivedData,"LoginSuccessData","firstLogin");

                        System.out.println("login successfull");

                        if (Integer.parseInt(firstlogin) == 1) {


                            Platform.runLater(()->{
                                try
                                {
                                    windowFuncs.createWindow("fxml/profileWindow.fxml","Chatrunner Beta v1.0",false,false);
                                    profileWindowController controller = (profileWindowController) windowFuncs.getCurrentController();
                                    controller.clientSocket = this.clientSocket;
                                    controller.username = username;
                                    windowFuncs.showStage(false);
                                    Stage curStage = (Stage) this.info_label.getScene().getWindow();
                                    curStage.close();
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }

                            });
                        }
                        else
                        {
                            //String imageByteList = xmlParser.getData(receivedData,"LoginSuccessData","imageBytes");

                            System.out.println("remember:"+rememberMe_checkBox.isSelected());

                                if (rememberMe_checkBox.isSelected()) {

                                    UserSettings settings = new UserSettings();

                                    settings.setUSERNAME(username_textField.getText());
                                    settings.setPASSWORD(password_passField.getText());
                                    settings.setREMEMBER(1);

                                    System.out.println("update settings");
                                    sqlite.updateSettings(settings);
                                }


                            String unmae = xmlParser.getData(receivedData,"LoginSuccessData","username");
                            String name = xmlParser.getData(receivedData,"LoginSuccessData","name");
                            String surname = xmlParser.getData(receivedData,"LoginSuccessData","surname");
                            String level = xmlParser.getData(receivedData,"LoginSuccessData","level");

                            //byte[] imageByte = Base64.getDecoder().decode(imageByteList);

                            byte[] image = clientSocket.readImage();
                            System.out.println("readed image");



                            SUserData userData = new SUserData(image,unmae,name,surname,Integer.parseInt(level));

                            Platform.runLater(()->{
                                try
                                {
                                    windowFuncs.createWindow("fxml/mainWindow.fxml","Chatrunner Beta v1.0",false,false);
                                    mainWindowController controller = (mainWindowController) windowFuncs.getCurrentController();
                                    controller.clientSocket = this.clientSocket;
                                    controller.userData = userData;
                                    windowFuncs.getScene().setFill(Color.TRANSPARENT);
                                    windowFuncs.getStage().initStyle(StageStyle.TRANSPARENT);
                                    windowFuncs.showStage(false);

                                    Stage curStage = (Stage) this.info_label.getScene().getWindow();
                                    curStage.close();
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }

                            });
                        }



                    }
                    else if (message.equals("log1n3rr0rCtt")){
                        Platform.runLater(()->{
                            info_label.setStyle("-fx-text-fill: pink");
                            info_label.setText("Bu Kullanıcı Zaten Bağlı");
                        });
                    }
                    else {
                        Platform.runLater(()->{
                            info_label.setStyle("-fx-text-fill: pink");
                            info_label.setText("kullanıcı adı veya şifre hatalı");
                        });
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(()->{
                        info_label.setStyle("-fx-text-fill: pink");
                        info_label.setText("Bağlantı Hatası 333");
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    public ClientSocket clientSocket = new ClientSocket();
    XmlParser xmlParser = new XmlParser();

    ReqFuncs reqFuncs = new ReqFuncs();

    WindowFuncs windowFuncs = new WindowFuncs();

    Sqlite sqlite = new Sqlite();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        Platform.runLater(()->{

            try
            {
                clientSocket.connect();

                UserSettings settings = sqlite.getSettings();

                if (settings != null)
                {
                     if (settings.getREMEMBER() == 1)
                     {
                         username_textField.setText(settings.getUSERNAME());
                         password_passField.setText(settings.getPASSWORD());

                         rememberMe_checkBox.setSelected(true);

                     }
                }




            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }


}
