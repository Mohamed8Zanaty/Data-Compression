package com.example.data_compression.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.BitSet;

public class FileHandler {

    // start zanaty
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

    public BinaryFileData readBinaryFile(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("compressed_file.bin"))) {
            // Read the Huffman tree root
            Huffman.HuffmanNode root = (Huffman.HuffmanNode) in.readObject();


            int dataLength = in.readInt();

            // Read the compressed bytes
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(temp)) != -1) {
                buffer.write(temp, 0, bytesRead);
            }
            byte[] compressedData = buffer.toByteArray();

            // Convert bytes back to BitSet
            BitSet bitSet = BitSet.valueOf(compressedData);

            // Reconstruct the original '0'/'1' bitstream string
            StringBuilder bitstream = new StringBuilder();
            for (int i = 0; i < dataLength; i++) {
                bitstream.append(bitSet.get(i) ? '1' : '0');
            }

            return new BinaryFileData(bitstream.toString(), dataLength, root);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    // end zanaty

  // Write encoded Image and Huffman codes to a file
//    public static void writeCompressedImage(String encodedImage, Map<Integer, String> huffmanCodes, String filePath) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            // Write Huffman codes (each pixel and its corresponding code)
//            writer.write("Huffman Codes:\n");
//            for (Map.Entry<Integer, String> entry : huffmanCodes.entrySet()) {
//                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
//            }
//
//            writer.write("Encoded Image:\n");
//            writer.write(encodedImage);
//
//            System.out.println("Data successfully written to " + filePath);
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//    }


    // start maro
    public static int[][] readImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
                    // If image is already grayscale, extract the single channel
                    pixels[y][x] = rgb & 0xFF;
                } else {
                    // Convert color image to grayscale using the luminance formula
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    pixels[y][x] = (int) (0.2989 * r + 0.5870 * g + 0.1140 * b);
                }
            }
        }
        return pixels;
    }

    public static void writeImage(int[][] pixels, String path) throws IOException {
        int height = pixels.length;
        int width = pixels[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = pixels[y][x];  // Grayscale value (0-255)
                int rgb = (gray << 16) | (gray << 8) | gray; // Convert grayscale to RGB format
                image.setRGB(x, y, rgb);
            }
        }
        ImageIO.write(image, "png", new File(path)); // Save as PNG
    }
    // end maro

    // start fatma
    public static void writeCompressedData(String data, Huffman.HuffmanNode root)  {
                try( ObjectOutputStream compressedFile = new ObjectOutputStream(new FileOutputStream("compressed_file.bin"))){
                    Huffman huffman = new Huffman();
                    if(root==null){
                        System.out.println("Root is null.");
                        return;
                    }
                    compressedFile.writeObject(root);
                    compressedFile.writeInt(data.length());
                    BitSet bits = new BitSet();
                    for(int i=0;i<data.length();i++){
                        if(data.charAt(i)=='1')
                        {
                            bits.set(i);
                        }
                    }
                    byte [] compressedData=bits.toByteArray();
                    compressedFile.write(compressedData);
                }catch(IOException e){
                e.printStackTrace();
            }
    }
    public static void writeDecompressedData(String data)  {
        try(FileWriter decompressedFile = new FileWriter("decompressed_file.txt")){
            decompressedFile.write(data);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    // end fatma
}
