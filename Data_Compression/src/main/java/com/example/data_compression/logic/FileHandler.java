package com.example.data_compression.logic;
import java.io.*;
import java.util.Map;
public class FileHandler {
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
