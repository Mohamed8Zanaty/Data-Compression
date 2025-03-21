package com.example.data_compression.logic;

public class BinaryFileData {
    private String data;
    private int size;
    private Huffman.HuffmanNode rootNode;

    public BinaryFileData(String data, int size, Huffman.HuffmanNode rootNode) {
        this.data = data;
        this.size = size;
        this.rootNode = rootNode;
    }

    public String getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public Huffman.HuffmanNode getRootNode() {
        return rootNode;
    }
}
