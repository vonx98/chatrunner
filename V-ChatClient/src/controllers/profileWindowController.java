package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.req_classes.ReqFuncs;
import data.req_classes.WindowFuncs;
import data.req_classes.XmlParser;
import data.socket_classes.ClientSocket;
import data.socket_classes.SUserData;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class profileWindowController implements Initializable {

    @FXML
    private ImageView profile_imageView;

    @FXML
    private Label info_label;

    @FXML
    private JFXTextField name_textField;

    @FXML
    private Label headerLabel;

    @FXML
    private Circle image_circle;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXTextField surname_textField;


    public String username;

    public Image image;

    @FXML
    void createProfileBtn_onAction() {

        try
        {

            String name = name_textField.getText();
            String surname = surname_textField.getText();


            if (!name.isEmpty() && !surname.isEmpty() && image != null) {

                if (update)
                {

                    String data = xmlParser.updateProfile("updateProfile",username,name,surname);


                    clientSocket.writeUTF(data);

                    clientSocket.sendImage(imagePath);

                    Platform.runLater(()->{

                        Stage curStage = (Stage) info_label.getScene().getWindow();
                        curStage.close();
                    });

                }
                else
                {
                    SUserData userData = new SUserData();

                    userData.setUsername(username);
                    userData.setName(name);
                    userData.setSurname(surname);
                    userData.setImage(reqFuncs.readFile(imagePath));
                    userData.setLevel(userData.getLevel());

                    String data = xmlParser.createProfileXml("createProfile",username,name,surname);
                    clientSocket.writeUTF(data);

                    clientSocket.sendImage(imagePath);

                    String success = clientSocket.readUTF();
                    System.out.println("sc:"+success);

                    if (success.equals("success")) {
                        Platform.runLater(()->{
                            try
                            {

                                windowFuncs.createWindow("fxml/mainWindow.fxml","Chatrunner Beta v1.0",false,false);
                                mainWindowController controller = (mainWindowController) windowFuncs.getCurrentController();

                                controller.userData = userData;
                                controller.clientSocket = this.clientSocket;
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

                    //String imageByteList = xmlParser.getData(receivedData,"LoginSuccessData","imageBytes");
                    //byte[] imageByte = Base64.getDecoder().decode(imageByteList);


                }

            }
            else {
                Platform.runLater(()->{
                    info_label.setTextFill(Color.RED);
                    info_label.setText("Lütfen Tüm Alanları Doldurunuz !");
                });
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void imageSelectBtn_onAction() throws FileNotFoundException {


        File file = fileChooser.showOpenDialog(this.info_label.getScene().getWindow());

        if (file != null) {

            imagePath = file.getAbsolutePath();

            InputStream stream = new FileInputStream(imagePath);

            image = new Image(stream);

            double height = image.getHeight();
            double width = image.getWidth();

            if (height <= 250 && width <= 250) {

                long kb = reqFuncs.getFileSizeNIO(file.getAbsolutePath());

                if (kb < 250) {
                    Platform.runLater(()->{
                        image_circle.setFill(new ImagePattern(image));
                    });
                }
                else {
                    Platform.runLater(()->{
                        info_label.setTextFill(Color.RED);
                        info_label.setText("Lütfen 250kb boyutundan küçük bir resim seçiniz");
                    });

                }
            }
            else
            {
                Platform.runLater(()->{
                    Platform.runLater(()->{
                        info_label.setStyle("-fx-text-fill: pink");
                        info_label.setText("Lütfen 250x250 boyutundan bir resim seçiniz");
                    });
                });
            }
        }





    }

    private String imagePath;
    public ClientSocket clientSocket;
    FileChooser fileChooser = new FileChooser();

    XmlParser xmlParser = new XmlParser();

    ReqFuncs reqFuncs = new ReqFuncs();

    WindowFuncs windowFuncs = new WindowFuncs();

    boolean update = false;

    public String name;

    public String surname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(()->{

            image_circle.setStroke(Color.BLUEVIOLET);
            image_circle.setEffect(new DropShadow(+5d,0D,+1D, Color.BLUE));

            if (update) {

                image_circle.setFill(new ImagePattern(image));

                headerLabel.setText("Update Profile");
                saveBtn.setText("Save");

                name_textField.setText(name);
                surname_textField.setText(surname);


            }
            else
            {
                try
                {
                    InputStream stream = new FileInputStream("1.png");
                    imagePath = "1.png";
                    image = new Image(stream);

                    image_circle.setFill(new ImagePattern(image));
                    headerLabel.setText("Create Profile");
                    saveBtn.setText("Create");
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        });

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Png Files", "*.png"));

    }


}
