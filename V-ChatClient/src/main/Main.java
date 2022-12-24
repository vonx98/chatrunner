package main;

import controllers.loginWindowController;
import controllers.mainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class Main extends Application {

    loginWindowController controller;
    FXMLLoader loader;
    @Override
    public void start(Stage stage) throws Exception {

        loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/fxml/loginWindow.fxml")));
        Parent root = loader.load();
        stage.setTitle("Arasaka Chat Beta v1.0");
        stage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(Objects.requireNonNull(this.getClass().getResource("/images/1.png")).toExternalForm())));
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("css/transparentBG.css");
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.show();

        this.controller = loader.getController();



    }

    @Override
    public void stop() throws IOException {
        controller.clientSocket.closeConnect();
        System.exit(1);

    }




}
