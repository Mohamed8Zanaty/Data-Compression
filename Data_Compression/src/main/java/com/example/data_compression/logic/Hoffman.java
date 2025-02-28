package com.example.data_compression.logic;

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
}
