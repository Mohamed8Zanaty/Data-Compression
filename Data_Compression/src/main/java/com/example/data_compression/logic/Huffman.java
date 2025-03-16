package com.example.data_compression.logic;
import java.util.HashMap;
public class Huffman {
    private  class HuffmanNode {
        public int value;
        public int frequency;
        public HuffmanNode left, right;
        HuffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }
    // ashraf
    public static void encode(HuffmanNode root, String s, HashMap<int, String> huffmancode) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            huffmancode.put(root.value, s);
        }
        encode(root.left, s + "0", huffmancode);
        encode(root.right, s + "1", huffmancode);
    }

    // fatma
    private static HashMap<int, String> generateCodes(HoffmanNode root){
        if (root==null)
            return null;
        HashMap<int, String> Codes = new HashMap<>();
        StringBuilder binaryCode = new StringBuilder();
        recursiveHelper(root,binaryCode,Codes);
        return Codes;
    }
    // fatma
    private static void recursiveHelper(HoffmanNode node,StringBuilder binaryCode,HashMap<int, String> Codes){
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
    public void generateHuffmanCodes(HuffmanNode curNode, String binCode, Map<Integer, String> pixelsCode) {
        if (curNode.right == null || curNode.left == null) {
            pixelsCode.put(curNode.frequency, binCode);
            return;
        }
        if (curNode.right != null) {
            generateHuffmanCodes(curNode.right, binCode += "1", pixelsCode);
        }
        if (curNode.left != null) {
            generateHuffmanCodes(curNode.left, binCode += "0", pixelsCode);
        }

    }

    // save huffman tree to be used in decoding as string
    // stringbuider for better memmory
    public void serializeHuffmanTree(HuffmanNode curNode, StringBuilder savedTree) {
        if (curNode.right == null && curNode.left == null) {
            savedTree.append("P").append(curNode.character).append(" ");
            return;
        } else {
            savedTree.append("OSC"); //internal node

        }
        serializeHuffmanTree( curNode.right, savedTree);
        serializeHuffmanTree( curNode.left, savedTree);

    }
    public static void storeHuffmanTree(HuffmanNode root,String fName) throws IOException {
        ObjectOutputStream writein=new ObjectOutputStream(new FileOutputStream(fName));
        writein.writeObject(root);
        System.out.println("sussefely storing tree");

    }
    public static HuffmanNode exportHuffmanTree( String fName) throws IOException, ClassNotFoundException {
        ObjectInputStream readout=new ObjectInputStream(new FileInputStream(fName));
        HuffmanNode r=  ( HuffmanNode ) readout.readObject();
        System.out.println("exporting Done succefullly ");
        return r;

}
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



}
