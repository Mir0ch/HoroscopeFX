package com.hallberg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        stage.getIcons().add(new Image("/images/h.png"));
        stage.setTitle("Horoscope FX");
        stage.setScene(new Scene(root, 600, 400));
        stage.setResizable(false);
        stage.show();
    }

    ///**
    // * @param args the command line arguments
    // */
    /*
    public static void main(String[] args) {
        launch(args);
    }
    */
}
            //You should either:

            //Move sample.fxml to src/main/resources. Or,
            //This requires changing your code to getResource("/sample.fxml").
            //Move sample.fxml to src/main/resources/sample.
            //With this option you can keep your code as is.