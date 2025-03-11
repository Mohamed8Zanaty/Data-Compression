package com.example.data_compression.logic;

import java.util.Map;

public class Huffman {
    private class HuffmanNode {
        public int value;
        public int frequency;
        public HuffmanNode left, right;

        HuffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
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

}
