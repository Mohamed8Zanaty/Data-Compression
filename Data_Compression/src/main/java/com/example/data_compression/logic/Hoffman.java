package com.example.data_compression.logic;
import java.util.HashMap;
public class Hoffman {
    private class HoffmanNode {
        public char character;
        public int frequency;
        public HoffmanNode left, right;
        HoffmanNode(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }
    }
        public static void encode(HoffmanNode root, String s, HashMap<Character, String> huffmancode) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            huffmancode.put(root.character, s);
        }
        encode(root.left, s + "0", huffmancode);
        encode(root.right, s + "1", huffmancode);
    }
    }
