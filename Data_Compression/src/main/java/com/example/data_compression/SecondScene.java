package com.example.data_compression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecondScene implements Initializable {
    @FXML
   private TextField labup;
    @FXML
    private Button browsebtn,startbtn,backId;
    @FXML
    AnchorPane upanchor;
    @FXML
   private ChoiceBox chbox;
    @FXML
    private String [] operations={"compress","decompress"};

    @FXML
    private void btnhandeler()throws IOException {
        final FileChooser fch=new FileChooser();
        fch.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        Stage st=(Stage) upanchor.getScene().getWindow();
        File file=fch.showOpenDialog(st);
        if(file!=null){
            labup.setText(file.getAbsolutePath());
        }


    }
    @FXML
    private void Startcompressionbtn() throws IOException {

        Parent root= FXMLLoader.load(getClass().getResource("comparsion.fxml"));
        Stage window =(Stage)startbtn.getScene().getWindow();

        window.setScene(new Scene(root));
        window.show();




//       System.out.println("شغاللللي");
    }
    @FXML
    private void backword() throws IOException {

        Parent root= FXMLLoader.load(getClass().getResource("main.fxml"));
        Stage window =(Stage)startbtn.getScene().getWindow();

        window.setScene(new Scene(root));
        window.show();




//       System.out.println("شغاللللي");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chbox.setValue("operation");
        chbox.getItems().addAll(operations);
    }
}
