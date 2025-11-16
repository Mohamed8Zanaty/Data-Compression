package com.example.data_compression.ui;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ImageStageController implements Initializable {
    @FXML
    private TextField pathTextField;
    @FXML
    private Button  startButton;
    @FXML
    private AnchorPane upAnchor;
    @FXML
    private ChoiceBox<String> operationBox;

    private final String [] operations = {"Compress","Decompress"};

    @FXML
    private void browseButtonHandler() throws IOException {
        final FileChooser fch=new FileChooser();
        fch.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BMP Files", "*.bmp"));
        Stage st=(Stage) upAnchor.getScene().getWindow();
        File file=fch.showOpenDialog(st);
        if(file == null) throw new FileNotFoundException();

        pathTextField.setText(file.getAbsolutePath());
    }
    @FXML
    private void startCompressionButton() throws IOException {
        System.out.println("Starting compression");
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("comparison.fxml")));
        Stage window =(Stage)startButton.getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }
    @FXML
    private void backword() throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Stage window =(Stage)startButton.getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        operationBox.setValue("Operation");
        operationBox.getItems().addAll(operations);
    }
}
