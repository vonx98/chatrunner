package data.req_classes;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class WindowFuncs {

    public Optional showAlert(String title, String headerText, String contentText, Alert.AlertType type)
    {
        Alert alert = new Alert(type);
        Stage stgs = (Stage) alert.getDialogPane().getScene().getWindow();
        stgs.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(this.getClass().getResource("/images/1.png").toExternalForm())));
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    private  Stage stage;

    private Scene scene;
    private Object Controller;

    private FXMLLoader loader;

    public void createWindow(String fxmlPath,String windowTitle,boolean maximized,boolean resizable) throws IOException {

        loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(this.getClass().getClassLoader().getResource(fxmlPath)));

        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);


        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.setMaximized(maximized);
        stage.setResizable(resizable);

        //stage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(this.getClass().getResource("/IMAGES/iconx64.png").toExternalForm())));


        this.stage = stage;
        this.scene = scene;
        this.Controller = loader.getController();


    }

    public void showStage(boolean showAndWait)
    {

        Platform.runLater(()->{
            if (showAndWait)
            {
                stage.showAndWait();
            }
            else
            {
                stage.show();
            }
        });
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Object getCurrentController() {
        return Controller;
    }

}
