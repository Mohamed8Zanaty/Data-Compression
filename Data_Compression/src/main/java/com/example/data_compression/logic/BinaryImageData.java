package com.example.data_compression.logic;

import java.io.*;
import java.util.BitSet;

public class BinaryImageData implements Serializable {
    private final HuffmanImage.Node huffmanTree;
    private final BitSet compressedData;
    private final int originalBitLength;
    private final int imageWidth;
    private final int imageHeight;

    // Constructor for creating new compressed file
    public BinaryImageData(HuffmanImage.Node huffmanTree, String bitstream, int width, int height) {
        this.huffmanTree = huffmanTree;
        this.imageWidth = width;
        this.imageHeight = height;
        this.originalBitLength = bitstream.length();
        this.compressedData = convertToBitSet(bitstream);
    }

    // Convert binary string to BitSet
    private BitSet convertToBitSet(String bitstream) {
        BitSet bitSet = new BitSet(bitstream.length());
        for (int i = 0; i < bitstream.length(); i++) {
            if (bitstream.charAt(i) == '1') {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    // Convert BitSet back to binary string
    public String getBitstream() {
        StringBuilder sb = new StringBuilder(originalBitLength);
        for (int i = 0; i < originalBitLength; i++) {
            sb.append(compressedData.get(i) ? '1' : '0');
        }
        return sb.toString();
    }
    // Getters
    public HuffmanImage.Node getHuffmanTree() {
        return huffmanTree;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }


}
