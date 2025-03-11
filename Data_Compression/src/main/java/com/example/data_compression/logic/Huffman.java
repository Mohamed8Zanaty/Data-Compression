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
}
