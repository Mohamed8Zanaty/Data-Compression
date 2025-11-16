package com.example.data_compression.ui;
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
          Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("image_stage.fxml")));
          Stage window =(Stage)imageButton.getScene().getWindow();
          window.setScene(new Scene(root));
          window.show();
   }
    @FXML
    private void textButtonHandler() throws IOException {
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("text_stage.fxml")));
        Stage window =(Stage)imageButton.getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }

}