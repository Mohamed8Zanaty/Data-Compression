package com.example.data_compression.logic;

public class Huffman {
    private class HuffmanNode {
        public int value;
        public int frequency;
        public HoffmanNode left, right;
        HuffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }
}
