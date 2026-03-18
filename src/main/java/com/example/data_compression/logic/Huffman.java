package com.example.data_compression.logic;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

public  class Huffman implements Serializable {
    public static class HuffmanNode implements Serializable {
        public int value;
        public int frequency;
        public HuffmanNode left, right;

        HuffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }
    public static HashMap<Integer, String> generateCodes(HuffmanNode root) {
        if (root == null)
            return null;
        HashMap<Integer, String> Codes = new HashMap<>();
        StringBuilder binaryCode = new StringBuilder();
        recursiveHelper(root, binaryCode, Codes);
        return Codes;
    }

    private static void recursiveHelper(HuffmanNode node, StringBuilder binaryCode, HashMap<Integer, String> Codes) {
        if (node.left == null && node.right == null) {
            Codes.put(node.value, binaryCode.toString());
            return;
        }
        binaryCode.append("1");
        recursiveHelper(node.right, binaryCode, Codes);
        int length = binaryCode.length();
        binaryCode.deleteCharAt(length - 1);
        binaryCode.append("0");
        recursiveHelper(node.left, binaryCode, Codes);
        binaryCode.deleteCharAt(length - 1);
    }

    //give each pixel binary code (less freq more bits)-(more freq less bits)
    public int[][] decodeHuffman(HuffmanNode root, String encodedString, int width, int height) {
        if (root == null || encodedString == null || encodedString.isEmpty()) {
            throw new IllegalArgumentException("Invalid input data.");
        }
        int[][] image = new int[height][width];
        HuffmanNode current = root;
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

    public static int compareHuffmanNodes(HuffmanNode a, HuffmanNode b) {
        return a.frequency - b.frequency;
    }

    public HuffmanNode buildHuffmanTree(HashMap<Integer, Integer> frequencyMap) {
        if (frequencyMap == null || frequencyMap.isEmpty()) {
            throw new IllegalArgumentException("Cringe Man!");
        }
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(Huffman::compareHuffmanNodes);

        // Create a Huffman node for each unique pixel value and add to priority queue
        for (HashMap.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Build the Huffman tree by merging the two least frequent nodes
        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();

            // Create a parent node with combined frequency
            HuffmanNode parent = new HuffmanNode(-1, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            queue.add(parent);
        }

        return queue.poll(); // Root of the Huffman tree
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

    public static void compressBMP(String originalPath, String compressedPath){
       Huffman huffman=new Huffman();
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
                HashMap<Integer, Integer> freqMap = huffman.getPixelFrequencies(diffChannel);
                Huffman.HuffmanNode tree = huffman.buildHuffmanTree(freqMap);
                HashMap<Integer, String> codes = Huffman.generateCodes(tree);
                String bitstream = Huffman.encodeImage(diffChannel, codes);

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

    public static void decompressBMP(String compressedPath, String outputPath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(compressedPath))) {

            BinaryImageData redChannel = (BinaryImageData) ois.readObject();
            BinaryImageData greenChannel = (BinaryImageData) ois.readObject();
            BinaryImageData blueChannel = (BinaryImageData) ois.readObject();


            Huffman huffman = new Huffman();

            // 3. Decode each channel
            int width = redChannel.getImageWidth();
            int height = redChannel.getImageHeight();

            int[][] rPixels = huffman.decodeHuffman(
                    redChannel.getHuffmanTree(),
                    redChannel.getBitstream(),
                    width, height);

            int[][] gPixels = huffman.decodeHuffman(
                    greenChannel.getHuffmanTree(),
                    greenChannel.getBitstream(),
                    width, height);

            int[][] bPixels = huffman.decodeHuffman(
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
