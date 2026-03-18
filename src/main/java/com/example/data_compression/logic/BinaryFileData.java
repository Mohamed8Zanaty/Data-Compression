package com.example.data_compression.logic;

public record BinaryFileData(String data, int size, Huffman.HuffmanNode rootNode) { }
