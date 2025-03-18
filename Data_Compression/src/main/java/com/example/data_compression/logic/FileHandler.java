package com.example.data_compression.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.*;
import java.util.Map;
public class FileHandler {
    public String readTextFile(String path) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n"); // Add line separator
            }
            String content = contentBuilder.toString();
            System.out.println(content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

    }
  // Write encoded Image and Huffman codes to a file
    public static void writeCompressedImage(String encodedImage, Map<Integer, String> huffmanCodes, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write Huffman codes (each pixel and its corresponding code)
            writer.write("Huffman Codes:\n");
            for (Map.Entry<Integer, String> entry : huffmanCodes.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }

            writer.write("Encoded Image:\n");
            writer.write(encodedImage);

            System.out.println("Data successfully written to " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }




}
