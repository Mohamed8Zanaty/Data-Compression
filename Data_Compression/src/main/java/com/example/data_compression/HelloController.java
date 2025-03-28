package com.example.data_compression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button imagebtn;


   @FXML
    private void btninmagehandeler() throws IOException {

          Parent root=FXMLLoader.load(getClass().getResource("second_scene.fxml"));
          Stage window =(Stage)imagebtn.getScene().getWindow();

          window.setScene(new Scene(root));
          window.show();




//       System.out.println("شغاللللي");


   }
    @FXML
    private void btninmagehandeler2() throws IOException {

        Parent root=FXMLLoader.load(getClass().getResource("Textstage.fxml"));
        Stage window =(Stage)imagebtn.getScene().getWindow();

        window.setScene(new Scene(root));
        window.show();




//       System.out.println("شغاللللي");


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}