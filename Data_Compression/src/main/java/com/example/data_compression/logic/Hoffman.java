package com.example.data_compression.logic;

import java.util.Map;

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

    //give each pixel binary code (less freq more bits)-(more freq less bits)
    public void generateHuffmanCodes(HoffmanNode curNode, String binCode, Map<Integer, String> pixelsCode) {
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
    public void serializeHuffmanTree(HoffmanNode curNode, StringBuilder savedTree) {
        if (curNode.right == null && curNode.left == null) {
            savedTree.append("P").append(curNode.character).append(" ");
            return;
        } else {
            savedTree.append("OSC"); //internal node 

        }
        serializeHuffmanTree( curNode.right, savedTree);
        serializeHuffmanTree( curNode.left, savedTree);

    }

}
