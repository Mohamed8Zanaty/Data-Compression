package com.example.data_compression.logic;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.BitSet;

public class FileHandler {
    public static BufferedImage convertTo24BitRGB(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_INT_RGB) {
            return image;
        }
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().drawImage(image, 0, 0, null);
        return newImage;
    }
    // Extracts R, G, B channels as separate 2D arrays
    public static int[][][] extractColorChannels(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] channels = new int[3][height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                channels[0][y][x] = (rgb >> 16) & 0xFF; // Red
                channels[1][y][x] = (rgb >> 8) & 0xFF;  // Green
                channels[2][y][x] = rgb & 0xFF;         // Blue
            }
        }
        return channels;
    }
    // Saves all three channels to one file
    public static void saveCompressedChannels(BinaryImageData[] channels, String path)
            throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(channels[0]); // Red
            oos.writeObject(channels[1]); // Green
            oos.writeObject(channels[2]); // Blue
        }

    }

    public static String getExtension(Path path) {
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;

    }
}
