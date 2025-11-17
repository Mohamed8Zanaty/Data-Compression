package com.example.data_compression.ui;
import com.example.data_compression.ui.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
public class MainStageController {

    @FXML
    private Button imageButton;
   @FXML
    private void imageButtonHandler() throws IOException {
       SceneManager.switchTo("/com/example/data_compression/ui/image_stage.fxml");
   }
    @FXML
    private void textButtonHandler() throws IOException {
        SceneManager.switchTo("/com/example/data_compression/ui/text_stage.fxml");
    }

}