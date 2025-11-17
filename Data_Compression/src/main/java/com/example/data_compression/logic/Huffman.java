package com.example.data_compression.logic;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

abstract public class Huffman  {
    public static class Node implements Comparable<Node>,  Serializable {
        int value;
        int freq;
        HuffmanText.Node left, right;
        Node(int value, int freq) {
            this.value = value;
            this.freq = freq;
        }
        Node(int value, int freq, HuffmanText.Node left, HuffmanText.Node right) {
            this.value = value;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        public boolean isLeaf() {
            return (left == null) && (right == null);
        }
        public int compareTo(HuffmanText.Node other) {
            return this.freq - other.freq;
        }
    }

    public static HuffmanText.Node buildHuffmanTree(HashMap<Integer, Integer> freqMap) {
        PriorityQueue<HuffmanText.Node> pq = new PriorityQueue<>();
        for (HashMap.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            pq.offer(new HuffmanText.Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() > 1) {
            HuffmanText.Node left = pq.poll();
            HuffmanText.Node right = pq.poll();
            // Internal nodes use a dummy character, here '\0'
            assert right != null;
            HuffmanText.Node parent = new HuffmanText.Node('\0', left.freq + right.freq, left, right);
            pq.offer(parent);
        }
        return pq.poll();
    }

    public static void generateCodes(Node root, String code, HashMap<Integer, String> codeMap) {
        if(root == null) return;
        if(root.isLeaf()) {
            codeMap.put(root.value, !code.isEmpty() ? code : "0");
        }
        generateCodes(root.left, code + '0', codeMap);
        generateCodes(root.right, code + '1', codeMap);
    }

    abstract public void compress(String inputFile, String outputFile) throws IOException;
    abstract public void decompress(String inputFile, String outputFile) throws IOException;

}
