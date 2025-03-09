package com.example.data_compression.logic;
import java.util.HashMap;
public class Hoffman {
    private  class HoffmanNode {
        public char character;
        public int frequency;
        public HoffmanNode left, right;
        HoffmanNode(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }
    }
 private static HashMap<Character, String> generateCodes(HoffmanNode root){
        if (root==null)
            return null;
        HashMap<Character, String> Codes = new HashMap<>();
        StringBuilder binaryCode = new StringBuilder();
        recursiveHelper(root,binaryCode,Codes);
        return Codes;
    }
    private static void recursiveHelper(HoffmanNode node,StringBuilder binaryCode,HashMap<Character, String> Codes){
        if(node.left==null && node.right==null){
            Codes.put(node.character,binaryCode.toString());
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
