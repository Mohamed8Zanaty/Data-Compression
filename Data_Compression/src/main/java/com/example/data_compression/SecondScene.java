package com.example.data_compression;
import com.example.data_compression.logic.FileHandler;
import com.example.data_compression.logic.Huffman;
import com.example.data_compression.logic.HuffmanDemo;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import kotlin.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static com.example.data_compression.logic.HuffmanDemo.inputAndOutputPaths;


public class SecondScene implements Initializable {
    @FXML
    private TextField labup;
    @FXML
    File savingDir;
    @FXML
    private Button browsebtn, startbtn, backId, savebtn;
    @FXML
    AnchorPane upanchor;
    @FXML
    private ChoiceBox chbox;
    @FXML
    private String[] operations = {"compress", "decompress"};
    @FXML
    PauseTransition delay = new PauseTransition(Duration.seconds(1));
    @FXML
    private Label result;
   @FXML
   Path p;
    File tempOutput = null;
    @FXML
    private void btnhandeler() throws IOException {
        final FileChooser fch = new FileChooser();
        Stage st = (Stage) upanchor.getScene().getWindow();
        File file = fch.showOpenDialog(st);
        p=file.toPath();
        if (file != null) {
            labup.setText(file.getAbsolutePath());
            savebtn.setDisable(false);
            chbox.setDisable(false);
        }


    }
    String ext;
    @FXML
    private void Startcompressionbtn() throws IOException {
        delay.setOnFinished(event -> {
            result.setText("");


        });

        if (chbox.getValue().equals("compress")) ext = "bin";
        else ext = "bmp";
        String realExt = FileHandler.getExtension(p);
        assert realExt != null;
        if (realExt.equals(ext)) {
            result.setText("File Extension Does Not Match The Operation Selected");
            result.setStyle("-fx-text-fill: red;");
        } else {
            if (ext.equals("bmp")){
                tempOutput = File.createTempFile("compressed_", ".bmp",savingDir);

                Huffman.decompressBMP(String.valueOf(labup.getText()), tempOutput.getAbsolutePath());
//                System.out.println("ll");
            }
            else{
                tempOutput = File.createTempFile("decompressed_", ".bin",savingDir);

                Huffman.compressBMP(String.valueOf(labup.getText()),tempOutput.getAbsolutePath());
//                System.out.println("jjjj");
            }

            labup.clear();
            chbox.setValue("operation");
            savingDir = null;
            startbtn.setDisable(true);
            chbox.setDisable(true);
            savebtn.setDisable(true);
            result.setText("Operation Succeeded");
            result.setStyle("-fx-text-fill: green;");
            delay.play();
        }


//       System.out.println("شغاللللي");
        }



        @FXML
        private void savbtnhandeler () {
            DirectoryChooser dicch = new DirectoryChooser();
            Stage st = (Stage) upanchor.getScene().getWindow();
            savingDir = dicch.showDialog(st);
            if (!labup.getText().isEmpty() && !chbox.getValue().equals("operation") && savingDir != null) {

                startbtn.setDisable(false);
            }

        }

        @FXML
        private void backword () throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Stage window = (Stage) startbtn.getScene().getWindow();

            window.setScene(new Scene(root));
            window.show();


//       System.out.println("شغاللللي");
        }


        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            chbox.setValue("operation");
            chbox.getItems().addAll(operations);
        }
    }