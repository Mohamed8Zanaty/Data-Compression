package com.example.data_compression;

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
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ResourceBundle;

public class Textstagecontroller implements Initializable {
    @FXML
    private TextField inputFilePathLabel;
    @FXML
    private Button startBtn, saveBtn;
    @FXML
    AnchorPane upAnchor;
    @FXML
    private ChoiceBox operationBox;
    @FXML
    private String [] operations={"compress","decompress"};
    @FXML
    private Label result;

    File savingDir;
    PauseTransition delay = new PauseTransition(Duration.seconds(1));

    @FXML
    private void btnhandeler()throws IOException {
        final FileChooser fch=new FileChooser();
//        fch.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("PNG Files", ".png"),
//                new FileChooser.ExtensionFilter("PDF Files", ".txt")
//        );
        Stage st=(Stage) upAnchor.getScene().getWindow();
        File file=fch.showOpenDialog(st);
        if(file!=null) {
            inputFilePathLabel.setText(file.getAbsolutePath());
            operationBox.setDisable(false);
            saveBtn.setDisable(false);
        }

    }
    @FXML
    private void Startcompressionbtn() throws IOException {
        delay.setOnFinished(event -> {
            result.setText("");
        });
        String ext;
        if(operationBox.getValue().equals("compress")) ext = "bin";
        else ext = "txt";
        Pair<Path,Path> Files = inputAndOutputPaths(ext);
        String realExt = FileHandler.getExtension(Files.getFirst());
        assert realExt != null;
        if(realExt.equals(ext)) {
            result.setText("File Extension Does Not Match The Operation Selected");
            result.setStyle("-fx-text-fill: red;");
        }
        else {
            if(ext.equals("bin"))
                HuffmanDemo.compress(String.valueOf(Files.getFirst()), String.valueOf(Files.getSecond()));
            else
                HuffmanDemo.decompress(String.valueOf(Files.getFirst()), String.valueOf(Files.getSecond()));

            inputFilePathLabel.clear();
            operationBox.setValue("operation");
            savingDir = null;
            startBtn.setDisable(true);
            operationBox.setDisable(true);
            saveBtn.setDisable(true);
            result.setText("Operation Succeeded");
            result.setStyle("-fx-text-fill: green;");
            delay.play();
        }

    }
    @FXML
    private void backword() throws IOException {

        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Stage window =(Stage)startBtn.getScene().getWindow();

        window.setScene(new Scene(root));
        window.show();

//       System.out.println("شغاللللي");
    }
    @FXML
    private  void savbtnhandeler(){
        DirectoryChooser dicch=new DirectoryChooser();
        Stage st=(Stage) upAnchor.getScene().getWindow();
        savingDir = dicch.showDialog(st);
        if(!inputFilePathLabel.getText().isEmpty() && !operationBox.getValue().equals("operation") && savingDir != null) {

            startBtn.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        operationBox.setValue("operation");
        operationBox.getItems().addAll(operations);
        if(!inputFilePathLabel.getText().isEmpty() && !operationBox.getItems().isEmpty()) {

        }
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


