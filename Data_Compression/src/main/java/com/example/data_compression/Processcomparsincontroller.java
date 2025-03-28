package com.example.data_compression;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Processcomparsincontroller implements Initializable {
    @FXML
   private  Button savebtn,backbtn;
    @FXML
   private AnchorPane ancorcom;
    @FXML
   private TextField labin;
   @FXML
   private  void savbtnhandeler(){
       DirectoryChooser dicch=new DirectoryChooser();
       Stage st=(Stage) ancorcom.getScene().getWindow();
      File file=dicch.showDialog(st);
      if(file!=null){
        labin.setText(file.getAbsolutePath());
      }
   }
    @FXML
    private void backword() throws IOException {

        Parent root= FXMLLoader.load(getClass().getResource("second_scene.fxml"));
        Stage window =(Stage)backbtn.getScene().getWindow();

        window.setScene(new Scene(root));
        window.show();




//       System.out.println("شغاللللي");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
