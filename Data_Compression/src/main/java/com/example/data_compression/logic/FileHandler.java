package com.example.data_compression.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileHandler {

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

}
