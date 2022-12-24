package data.desing_classes;

import data.req_classes.ReqFuncs;
import data.socket_classes.SUserData;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;

public class CustomListCell extends ListCell<SUserData> {

    private  HBox content;
    private Text username;
    private Text name;
    private Text level;

    private Circle circle;


    public CustomListCell() {
        super();

        username = new Text();

        name = new Text();

        level = new Text();

        circle = new Circle();


        username.setFill(Color.rgb(153,130,0));
        username.setStyle("-fx-font-weight: bold;" +
                "-fx-font: 11 Calibri;");

        name.setFill(Color.BLACK);
        name.setStyle("-fx-font: 15 Calibri;" +
                "-fx-font-weight: bold;");

        level.setFill(Color.rgb(116,97,237));
        level.setStyle("-fx-font: 10 Calibri;");


        circle.setRadius(20);




        VBox vBox = new VBox(name,username, level);
        vBox.setSpacing(2);

        content = new HBox(circle, vBox);

        content.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 1; -fx-border-color: lightgrey;");
        content.setPadding(new Insets(5, 5, 5, 5));
        content.setSpacing(5);

        //DropShadow dropShadow = new DropShadow();
        //content.setEffect(dropShadow);

        content.setSpacing(10);
    }

    ReqFuncs reqFuncs = new ReqFuncs();
    @Override
    protected void updateItem(SUserData item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && !empty)
        {
            String uname = item.getUsername();
            try
            {
                Image image = reqFuncs.byteToImage(item.getImage());

                name.setText(item.getName() +" "+item.getSurname());
                username.setText(uname);
                level.setText("Seviye "+item.getLevel());

                circle.setFill(new ImagePattern(image));
                if (uname.equals("vonx98")) {
                    circle.setStroke(Color.BLUEVIOLET);
                    circle.setEffect(new DropShadow(+10d,0D,+2D, Color.BLUE));
                }
                else {
                    circle.setStroke(null);
                    circle.setEffect(null);
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            setGraphic(content);
        }
        else
        {
            setGraphic(null);
        }
    }
}
