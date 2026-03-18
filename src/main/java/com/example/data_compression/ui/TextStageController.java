package com.example.data_compression.ui;

import com.example.data_compression.logic.FileHandler;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ResourceBundle;

public class TextStageController implements Initializable {
    @FXML
    private TextField inputFilePathLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button saveAsButton;
    @FXML
    AnchorPane upAnchor;
    @FXML
    private ChoiceBox<String> operationBox;
    @FXML
    private Label result;

    private final String [] operations={"Compress","Decompress"};
    private File savingDir;
    private final PauseTransition delay = new PauseTransition(Duration.seconds(1));

    @FXML
    private void browseButtonHandler() throws IOException {
        final FileChooser fch = new FileChooser();
        Stage st = (Stage) upAnchor.getScene().getWindow();
        File file=fch.showOpenDialog(st);
        if (file == null) throw new FileNotFoundException("File Not Found");
        inputFilePathLabel.setText(file.getAbsolutePath());
        operationBox.setDisable(false);
        saveAsButton.setDisable(false);
    }
    @FXML
    private void startCompressionButton() throws IOException {
        delay.setOnFinished(event -> result.setText(""));
        String extension;
        if(operationBox.getValue().equals("compress")) extension = "bin";
        else extension = "txt";
        Pair<Path,Path> Files = inputAndOutputPaths(extension);
        String realExt = FileHandler.getExtension(Files.getFirst());
        assert realExt != null;
        if(realExt.equals(extension)) {
            result.setText("File Extension Does Not Match The Operation Selected");
            result.setStyle("-fx-text-fill: red;");
        }
        else {
            if(extension.equals("bin"))
                HuffmanDemo.compress(String.valueOf(Files.getFirst()), String.valueOf(Files.getSecond()));
            else
                HuffmanDemo.decompress(String.valueOf(Files.getFirst()), String.valueOf(Files.getSecond()));

            inputFilePathLabel.clear();
            operationBox.setValue("Operation");
            savingDir = null;
            startButton.setDisable(true);
            operationBox.setDisable(true);
            saveAsButton.setDisable(true);
            result.setText("Operation Succeeded");
            result.setStyle("-fx-text-fill: green;");
            delay.play();
        }

    }
    @FXML
    private void backword() throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Stage window =(Stage)startButton.getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }
    @FXML
    private  void saveButtonHandler(){
        DirectoryChooser directoryChooser=new DirectoryChooser();
        Stage st=(Stage) upAnchor.getScene().getWindow();
        savingDir = directoryChooser.showDialog(st);
        if(!inputFilePathLabel.getText().isEmpty() && !operationBox.getValue().equals("operation") && savingDir != null) {

            startButton.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        operationBox.setValue("Operation");
        operationBox.getItems().addAll(operations);
    }

    private  Pair<Path, Path> inputAndOutputPaths(String extensionFilter) {
        Path selectedFile = Path.of(inputFilePathLabel.getText());
        String fileName = selectedFile.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        String nameWithoutExtension = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        Path parentDir = savingDir.toPath();
        Path outputFilePath;
        if(extensionFilter.equals("bin"))
            outputFilePath = parentDir.resolve(nameWithoutExtension + ".bin");
        else outputFilePath = parentDir.resolve(nameWithoutExtension + ".txt");
        return new Pair<>(selectedFile, outputFilePath);
    }

}


