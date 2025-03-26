package com.example.data_compression;
import com.example.data_compression.logic.BinaryFileData;
import com.example.data_compression.logic.FileHandler;
import com.example.data_compression.logic.Huffman;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
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
        launch();
//        String ImagePath="D:\\Student-Activities\\OSC-s&t\\download.png";
//        String path2="D:\\Student-Activities\\OSC-s&t\\compressed_image\\imahe.bin";
//        String backPath="D:\\Student-Activities\\OSC-s&t\\compressed_image\\bbb.png";
//        int[][] originalPixels = FileHandler.readImage(ImagePath);
//        System.out.println("Original image read successfully.");
////        for(int i=0;i<4;i++){
////            for(int k=0;k<4;k++){
////                System.out.println(originalPixels[i][k]);
////
////            }
////        }
//        System.out.println("Compressing the image...");
//        Huffman huffman = new Huffman();
//        HashMap<Integer, Integer> frequencyMap = huffman.getPixelFrequencies(originalPixels);
////        for(Integer it :frequencyMap.values() ){
////            System.out.println(it);
////        }
//        Huffman.HuffmanNode root = huffman.buildHuffmanTree(frequencyMap);
//        HashMap<Integer, String> huffmanCodes = Huffman.generateCodes(root);
//        String encodedImage = Huffman.encodeImage(originalPixels, huffmanCodes);
////        System.out.println(encodedImage);
//        // Write compressed data to a file
//        FileHandler.writeCompressedData(encodedImage, root,path2);
//        System.out.println("Image compressed and saved to " + path2);
//
//        System.out.println("Decompressing the image...");
//        BinaryFileData compressedData = FileHandler.readBinaryFile(path2);
//        String encodedString = compressedData.getData();
//        Huffman.HuffmanNode decompressRoot = compressedData.getRootNode();
//        // Decode the image
//        int[][] decompressedPixels = huffman.decodeHuffman(decompressRoot, encodedString, originalPixels[0].length, originalPixels.length);
//
//         FileHandler.writeImage(decompressedPixels,backPath);
//        System.out.println("Image decompressed and saved to " + backPath);
//


    }
}