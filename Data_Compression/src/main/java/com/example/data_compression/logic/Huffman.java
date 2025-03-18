package com.example.data_compression.logic;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {
    private static class HuffmanNode {
        public int value;
        public int frequency;
        public HuffmanNode left, right;
        HuffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }
    // ashraf
    public static void encode(HuffmanNode root, String s, HashMap<Integer, String> huffmanCode) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.value, s);
        }
        encode(root.left, s + "0", huffmanCode);
        encode(root.right, s + "1", huffmanCode);
    }

    // fatma
    private static HashMap<Integer, String> generateCodes(HuffmanNode root){
        if (root==null)
            return null;
        HashMap<Integer, String> Codes = new HashMap<>();
        StringBuilder binaryCode = new StringBuilder();
        recursiveHelper(root,binaryCode,Codes);
        return Codes;
    }
    // fatma
    private static void recursiveHelper(HuffmanNode node,StringBuilder binaryCode,HashMap<Integer, String> Codes){
        if(node.left==null && node.right==null){
            Codes.put(node.value,binaryCode.toString());
            return;
        }
        binaryCode.append("1");
        recursiveHelper(node.right,binaryCode,Codes);
        int length = binaryCode.length();
        binaryCode.deleteCharAt(length-1);
        binaryCode.append("0");
        recursiveHelper(node.left,binaryCode,Codes);
        binaryCode.deleteCharAt(length-1);
    }

    //give each pixel binary code (less freq more bits)-(more freq less bits)
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

    // save huffman tree to be used in decoding as string
    // stringbuider for better memmory
    public void serializeHuffmanTree(HuffmanNode curNode, StringBuilder savedTree) {
        if (curNode.right == null && curNode.left == null) {
            savedTree.append("P").append(curNode.value).append(" ");
            return;
        } else {
            savedTree.append("OSC"); //internal node

        }
        assert curNode.right != null;
        serializeHuffmanTree( curNode.right, savedTree);
        serializeHuffmanTree( curNode.left, savedTree);

    }

    //Image Methods


    //
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






    //
    public static String encodeImage(int[][] image, HashMap<Integer, String> huffmanCodes){

        StringBuilder encodedImage = new StringBuilder(); //StringBuilder is created to efficiently build the encoded string
        for (int[] row : image) {
            for (int pixel : row) {
                encodedImage.append(huffmanCodes.get(pixel));
            }
        }

        return  encodedImage.toString();

    }
}
