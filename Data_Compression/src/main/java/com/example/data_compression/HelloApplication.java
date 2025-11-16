package com.example.data_compression;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage)  {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ui/main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Data_Compression!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
           System.out.println("There was an error loading the FXML");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}