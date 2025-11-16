package com.example.data_compression.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class ComparisonController {
    @FXML
    private  Button backButton;
    @FXML
    private AnchorPane stage;
    @FXML
    private TextField pathTextField;

   @FXML
   private  void saveButtonHandler(){
       DirectoryChooser directoryChooser=new DirectoryChooser();
       Stage st=(Stage) stage.getScene().getWindow();
      File file = directoryChooser.showDialog(st);
      if(file!=null){
          pathTextField.setText(file.getAbsolutePath());
      }
   }
    @FXML
    private void backword() throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("image_stage.fxml")));
        Stage window =(Stage)backButton.getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }

}
