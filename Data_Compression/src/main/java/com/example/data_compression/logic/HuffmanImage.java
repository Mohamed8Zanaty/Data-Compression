package com.example.data_compression.logic;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

import static com.example.data_compression.logic.Huffman.buildHuffmanTree;

public  class HuffmanImage extends Huffman {



    //give each pixel binary code (less freq more bits)-(more freq less bits)
    public int[][] decodeHuffman(Node root, String encodedString, int width, int height) {
        if (root == null || encodedString == null || encodedString.isEmpty()) {
            throw new IllegalArgumentException("Invalid input data.");
        }
        int[][] image = new int[height][width];
        Node current = root;
        int row = 0, col = 0;

        for (char bit : encodedString.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;
            assert current != null;
            if (current.left == null && current.right == null) {
                image[row][col] = current.value;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
                current = root;
                if (row == height) break;
            }
        }
        return image; // Return the reconstructed image
    }

    public HashMap<Integer, Integer> getPixelFrequencies(int[][] image) {
        if (image == null || image.length == 0) {
            throw new IllegalArgumentException("Mat7otesh 7aga fadya");
        }
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();

        for (int[] row : image) {
            for (int pixel : row) {
                frequencyMap.put(pixel, frequencyMap.getOrDefault(pixel, 0) + 1);
            }
        }
        return frequencyMap;
    }

    public static int compareNodes(Node a, Node b) {
        return a.freq - b.freq;
    }

    

    public static String encodeImage(int[][] image, HashMap<Integer, String> huffmanCodes) {
        StringBuilder encodedImage = new StringBuilder(); //StringBuilder is created to efficiently build the encoded string
        for (int[] row : image) {
            for (int pixel : row) {
                encodedImage.append(huffmanCodes.get(pixel));
            }
        }
        return encodedImage.toString();
    }

    public static int[][] applyDifferentialEncoding(int[][] channel) {
        int[][] diff = new int[channel.length][channel[0].length];
        int prev = 0;
        for (int y = 0; y < channel.length; y++) {
            for (int x = 0; x < channel[y].length; x++) {
                diff[y][x] = channel[y][x] - prev;
                prev = channel[y][x];
            }
        }
        return diff;
    }
    private static int[][] inverseDifferential(int[][] diffChannel) {
        int[][] channel = new int[diffChannel.length][diffChannel[0].length];
        int prev = 0;
        for (int y = 0; y < diffChannel.length; y++) {
            for (int x = 0; x < diffChannel[y].length; x++) {
                channel[y][x] = diffChannel[y][x] + prev;
                prev = channel[y][x];
                // Clamp to 0-255 range
                channel[y][x] = Math.max(0, Math.min(255, channel[y][x]));
            }
        }
        return channel;
    }

    @Override
    public void compress(String originalPath, String compressedPath){
       HuffmanImage huffmanImage =new HuffmanImage();
        try {
            // 1. Read BMP file
            BufferedImage bmpImage = null;
            try {
                File inputFile = new File(originalPath);

                if (!inputFile.exists()) {
                    System.err.println("Error: File not found - " +originalPath);
                    return;
                }

                bmpImage = ImageIO.read(inputFile);
                if (bmpImage == null) {
                    System.err.println("Error: Unsupported BMP format or corrupt file");
                    return;
                }

            } catch (IOException e) {
                System.err.println("Error processing BMP:");
                e.printStackTrace();
            }

            // 2. Convert to 24-bit RGB if needed
            assert bmpImage != null;
            BufferedImage rgbImage = FileHandler.convertTo24BitRGB(bmpImage);
            int width = rgbImage.getWidth();
            int height = rgbImage.getHeight();
            // 3. Proceed with your Huffman compression
            System.out.println("BMP loaded successfully. Dimensions: " +
                    rgbImage.getWidth() + "x" + rgbImage.getHeight());

            // 3. Convert to pixel array and separate channels
            int[][][] channels =FileHandler.extractColorChannels(rgbImage);
            // 4. Compress each channel separately
            BinaryImageData[] compressedChannels = new BinaryImageData[3];
            for (int c = 0; c < 3; c++) {
                // Apply differential encoding to increase compression
                int[][] diffChannel = applyDifferentialEncoding(channels[c]);
                HashMap<Integer, Integer> freqMap = huffmanImage.getPixelFrequencies(diffChannel);
                HuffmanImage.Node tree = buildHuffmanTree(freqMap);
                HashMap<Integer, String> codes = new HashMap<>();
                String binaryCode = "";
                generateCodes(tree, binaryCode, codes);
                String bitstream = HuffmanImage.encodeImage(diffChannel, codes);

                compressedChannels[c] = new BinaryImageData(tree, bitstream, width, height);
            }
            System.out.println("completed comprsed 1");
            // 5. Save all channels to one file

            FileHandler.saveCompressedChannels(compressedChannels, compressedPath);
            // 6. Calculate compression ratio
            File originalFile = new File(originalPath);
            File compressedFile = new File(compressedPath);
            System.out.printf("Original BMP: %d bytes\n", originalFile.length());
            System.out.printf("Compressed: %d bytes\n", compressedFile.length());
            System.out.printf("Ratio: %.2f:1\n",
                    (double) originalFile.length() / compressedFile.length());

        } catch (IOException e) {
            System.err.println("Error processing BMP:");
            e.printStackTrace();
        }

    }

    public void decompress(String compressedPath, String outputPath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(compressedPath))) {

            BinaryImageData redChannel = (BinaryImageData) ois.readObject();
            BinaryImageData greenChannel = (BinaryImageData) ois.readObject();
            BinaryImageData blueChannel = (BinaryImageData) ois.readObject();


            HuffmanImage huffmanImage = new HuffmanImage();

            // 3. Decode each channel
            int width = redChannel.getImageWidth();
            int height = redChannel.getImageHeight();

            int[][] rPixels = huffmanImage.decodeHuffman(
                    redChannel.getHuffmanTree(),
                    redChannel.getBitstream(),
                    width, height);

            int[][] gPixels = huffmanImage.decodeHuffman(
                    greenChannel.getHuffmanTree(),
                    greenChannel.getBitstream(),
                    width, height);

            int[][] bPixels = huffmanImage.decodeHuffman(
                    blueChannel.getHuffmanTree(),
                    blueChannel.getBitstream(),
                    width, height);

            // 4. Apply inverse differential encoding
            rPixels = inverseDifferential(rPixels);
            gPixels = inverseDifferential(gPixels);
            bPixels = inverseDifferential(bPixels);

            // 5. Reconstruct RGB image
            BufferedImage reconstructed = new BufferedImage(
                    width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = (rPixels[y][x] << 16) |
                            (gPixels[y][x] << 8) |
                            bPixels[y][x];
                    reconstructed.setRGB(x, y, rgb);
                }
            }

            // 6. Save as BMP
            ImageIO.write(reconstructed, "bmp", new File(outputPath));
            System.out.println("Decompression successful. Saved to: " + outputPath);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during decompression:");
            e.printStackTrace();
        }
    }

}
