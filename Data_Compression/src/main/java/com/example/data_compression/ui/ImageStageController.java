package com.example.data_compression.ui;
import com.example.data_compression.logic.FileHandler;
import com.example.data_compression.logic.Huffman;
import com.example.data_compression.ui.util.SceneManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class ImageStageController implements Initializable {
    @FXML
    private TextField pathTextField;
    @FXML
    File savingDir;

    @FXML
    private Button startButton;
    @FXML
    private Button saveButton;
    @FXML
    private AnchorPane upAnchor;
    @FXML
    private ChoiceBox<String> operationBox;
    @FXML
    private final String[] operations = {"compress", "decompress"};
    @FXML
    PauseTransition delay = new PauseTransition(Duration.seconds(1));
    @FXML
    private Label result;
    @FXML
    Path p;
    File tempOutput = null;
    @FXML
    private void browseButtonHandler(){
        final FileChooser fch = new FileChooser();
        Stage st = (Stage) upAnchor.getScene().getWindow();
        File file = fch.showOpenDialog(st);
        p=file.toPath();
        pathTextField.setText(file.getAbsolutePath());
        saveButton.setDisable(false);
        operationBox.setDisable(false);


    }
    String ext;
    @FXML
    private void startCompressionButton() throws IOException {
        delay.setOnFinished(event -> {
            result.setText("");
        });

        if (operationBox.getValue().equals("compress")) ext = "bin";
        else ext = "bmp";
        String realExt = FileHandler.getExtension(p);
        assert realExt != null;
        if (realExt.equals(ext)) {
            result.setText("File Extension Does Not Match The Operation Selected");
            result.setStyle("-fx-text-fill: red;");
        } else {
            if (ext.equals("bmp")){
                tempOutput = File.createTempFile("compressed_", ".bmp",savingDir);
                Huffman.decompressBMP(String.valueOf(pathTextField.getText()), tempOutput.getAbsolutePath());
            }
            else{
                tempOutput = File.createTempFile("decompressed_", ".bin",savingDir);
                Huffman.compressBMP(String.valueOf(pathTextField.getText()),tempOutput.getAbsolutePath());
            }
            pathTextField.clear();
            operationBox.setValue("operation");
            savingDir = null;
            startButton.setDisable(true);
            operationBox.setDisable(true);
            saveButton.setDisable(true);
            result.setText("Operation Succeeded");
            result.setStyle("-fx-text-fill: green;");
            delay.play();
        }
    }



    @FXML
    private void savbtnhandeler () {
        DirectoryChooser dicch = new DirectoryChooser();
        Stage st = (Stage) upAnchor.getScene().getWindow();
        savingDir = dicch.showDialog(st);
        if (!pathTextField.getText().isEmpty() && !operationBox.getValue().equals("operation") && savingDir != null) {

            startButton.setDisable(false);
        }

    }

    @FXML
    private void backword () {
        try {
            SceneManager.goBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        operationBox.setValue("Operation");
        operationBox.getItems().addAll(operations);
    }
}
