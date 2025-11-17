package com.example.data_compression;

import com.example.data_compression.ui.util.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage)  {
        try{
            SceneManager.setStage(stage);
            SceneManager.setInitialScene("/com/example/data_compression/ui/main.fxml");
            stage.setTitle("Data_Compression!");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}