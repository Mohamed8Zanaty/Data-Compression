package com.example.data_compression.logic;
import java.util.HashMap;
public class Huffman {
    private class HuffmanNode {
        public int value;
        public int frequency;
        public HuffmanNode left, right;
        HoffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }
        public static void encode(HuffmanNode root, String s, HashMap<int, String> huffmancode) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            huffmancode.put(root.value, s);
        }
        encode(root.left, s + "0", huffmancode);
        encode(root.right, s + "1", huffmancode);
    }
    }
