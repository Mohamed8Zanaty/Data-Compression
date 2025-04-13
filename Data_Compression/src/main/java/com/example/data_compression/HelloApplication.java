package com.example.data_compression;
import com.example.data_compression.logic.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Style.css")).toExternalForm());

            stage.setTitle("Data_Compression!");

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) throws IOException {
//        Huffman HH=new Huffman();
//        String originalPath="D:\\Student-Activities\\OSC-s&t\\compressed_image\\sample_1280×853.bmp",
//                compressedPath="D:\\Student-Activities\\OSC-s&t\\compressed_image\\compressed_bmp.bin",
//        outpath="D:\\Student-Activities\\OSC-s&t\\compressed_image\\de.bmp";
//       HH.compressBMP(originalPath,compressedPath);
//       HH.decompressBMP(compressedPath,outpath);
        launch();
    }










}

