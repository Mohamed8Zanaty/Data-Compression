package com.example.data_compression.logic;
import java.io.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

public  class Huffman implements Serializable {
    public class HuffmanNode implements Serializable {
        public int value;
        public int frequency;
        public HuffmanNode left, right;

        HuffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }

    // start fatma

    private HuffmanNode root;
    public HuffmanNode getRoot() {

        return this.root;
    }

    public String compress(String data) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < data.length(); i++) {
            int character = (int) data.charAt(i);
            if (frequencyMap.containsKey(character)) {
                frequencyMap.put(character, (frequencyMap.get(character) + 1));
            } else {
                frequencyMap.put(character, 1);
            }
        }
        this.root = buildHuffmanTree(frequencyMap);
        HashMap<Integer, String> Codes = new HashMap<>();
        Codes = generateCodes(root);
        String compresedText = encodeText(data, Codes);
        return compresedText;
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
    // end fatma

    // start marwa
    //give each pixel binary code (less freq more bits)-(more freq less bits)
    //repeatedddddd***********************
    public void generateHuffmanCodes(HuffmanNode curNode, String binCode, HashMap<Integer, String> pixelsCode) {
        if (curNode.right == null || curNode.left == null) {
            pixelsCode.put(curNode.frequency, binCode);
            return;
        }
        generateHuffmanCodes(curNode.right, binCode += "1", pixelsCode);
        if (curNode.left != null) {
            generateHuffmanCodes(curNode.left, binCode += "0", pixelsCode);
        }

    }

//    public static void storeHuffmanTree(HuffmanNode root, String fName) throws IOException {
//        ObjectOutputStream writein = new ObjectOutputStream(new FileOutputStream(fName));
//        writein.writeObject(root);
//        System.out.println("sussefely storing tree");
//
//    }
//
//    public static HuffmanNode exportHuffmanTree(String fName) throws IOException, ClassNotFoundException, IOException {
//        ObjectInputStream readout = new ObjectInputStream(new FileInputStream(fName));
//        HuffmanNode r = (HuffmanNode) readout.readObject();
//        System.out.println("exporting Done succefullly ");
//        return r;
//
//    }

    public int[][] decodeHuffman(HuffmanNode root, String encodedString, int width, int height) {
        if (root == null || encodedString == null || encodedString.isEmpty()) {
            throw new IllegalArgumentException("Invalid input data.");
        }

        int[][] image = new int[height][width];
        HuffmanNode current = root;
        int row = 0, col = 0;

        for (char bit : encodedString.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;


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
    // end marwa

    // start maro
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
    // end maro

    // start shahd
    public static String encodeImage(int[][] image, HashMap<Integer, String> huffmanCodes) {
        StringBuilder encodedImage = new StringBuilder(); //StringBuilder is created to efficiently build the encoded string
        for (int[] row : image) {
            for (int pixel : row) {
                encodedImage.append(huffmanCodes.get(pixel));
            }
        }
        return encodedImage.toString();
    }
    // end shahd

    // start ashraf
    public static String encodeText(String text, HashMap<Integer, String> huffmanCode) {
        StringBuilder encodedString = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encodedString.append(huffmanCode.get(ch));
        }
        return encodedString.toString();
    }

    public static void decode(HuffmanNode root, int[] index, String s) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            System.out.print(root.value);
            return;
        }
        if (index[0] < s.length() - 1) {
            index[0]++;
            if (s.charAt(index[0]) == '0')
                decode(root.left, index, s);
            else
                decode(root.right, index, s);
        }
    }
    // end ashraf
}
