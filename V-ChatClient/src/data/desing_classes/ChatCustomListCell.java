package data.desing_classes;

import data.req_classes.ReqFuncs;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import sql.UserSettings;

public class ChatCustomListCell extends ListCell<TextData> {


    private HBox content;

    private VBox vBox;
    private Text name;
    private Text text;

    private Text time;

    private Circle circle;




    public ChatCustomListCell() {

        super();

        name = new Text();
        text = new Text();
        time = new Text();
        circle = new Circle();

        name.setFill(Color.BLACK);
        name.setStyle("-fx-font: 12 Calibri;");

        text.setFill(Color.BLACK);
        text.setStyle("-fx-font: 18 Calibri;");


        time.setFill(Color.LIGHTGREY);
        time.setStyle("-fx-font: 10 Calibri;");


        HBox hBox = new HBox(text,time);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        hBox.setSpacing(5);

        vBox = new VBox(name,hBox);
        content = new HBox(circle, vBox);


        circle.setRadius(25);
        circle.setStroke(Color.BLUEVIOLET);


        vBox.setPadding(new Insets(8, 8, 8, 8));
        content.setSpacing(10);
        content.setPadding(new Insets(5, 5, 5, 5));

    }

    @Override
    protected void updateItem(TextData item, boolean empty) {


        super.updateItem(item, empty);


        if (item != null && !empty)
        {
            String username = item.getUsername();



            if (item.isLeft())
            {
                if (username.equals("vonx98"))
                {
                    name.setFill(Color.WHITE);
                    time.setFill(Color.LIGHTGREY);
                    circle.setEffect(new DropShadow(+10d,0D,+2D, Color.BLUE));
                    content.setEffect(new DropShadow(+5d,0D,+1D, Color.BLUE));
                    vBox.setStyle("-fx-background-color:  #9481f5; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 0.15");

                }
                else {
                    time.setFill(Color.GREY);
                    name.setFill(Color.BLACK);
                    circle.setEffect(null);
                    content.setEffect(null);
                    vBox.setStyle("-fx-background-color:  white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 0.15");

                }
                name.setVisible(true);
                circle.setVisible(true);
                content.setAlignment(Pos.CENTER_LEFT);
            }
            else
            {
                time.setFill(Color.GREY);
                if (username.equals("vonx98"))
                {

                    name.setFill(Color.valueOf("#9481f5"));
                    content.setEffect(new DropShadow(+5d,0D,+1D, Color.BLUE));
                }
                else {
                    name.setFill(Color.BLACK);
                    circle.setEffect(null);
                    content.setEffect(null);
                }
                circle.setVisible(false);
                name.setVisible(false);
                content.setAlignment(Pos.CENTER_RIGHT);
                vBox.setStyle("-fx-background-color:  #d9fdd3; -fx-border-radius: 10; -fx-background-radius: 10;-fx-border-color: black; -fx-border-width: 0.15");
                Platform.runLater(()->{

                });
            }

            circle.setFill(new ImagePattern(item.getImage()));



            time.setText(item.getTime());
            name.setText(item.getName());
            text.setText(item.getText());

            setGraphic(content);
        }
        else
        {
            setGraphic(null);
        }
    }

}
