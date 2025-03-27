package com.example.data_compression.logic;

import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanDemo {
    static class Node implements Comparable<Node> {
        int value;
        int freq;
        Node left, right;
        Node(int value, int freq) {
            this.value = value;
            this.freq = freq;
        }
        Node(int value, int freq, Node left, Node right) {
            this.value = value;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        public boolean isLeaf() {
            return (left == null) && (right == null);
        }
        public int compareTo(Node other) {
            return this.freq - other.freq;
        }
    }
    public static HashMap<Integer, Integer> buildFrequencyMap(String data) {
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        for (char ch : data.toCharArray()) {
            freqMap.put((int)ch, freqMap.getOrDefault((int)ch, 0) + 1);
        }
        freqMap.put(256, 1);
        return freqMap;
    }
    public static Node buildHuffmanTree(HashMap<Integer, Integer> freqMap) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (HashMap.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            pq.offer(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            // Internal nodes use a dummy character, here '\0'
            assert right != null;
            Node parent = new Node('\0', left.freq + right.freq, left, right);
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
    public static void writeHeader(DataOutputStream dos, HashMap<Integer, Integer> freqMap) throws IOException {
        dos.writeInt(freqMap.size());
        for (HashMap.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            dos.writeInt(entry.getKey());
            dos.writeInt(entry.getValue());
        }
    }
    public static HashMap<Integer, Integer> readHeader(DataInputStream dis) throws IOException {
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            int value = dis.readInt();
            int freq = dis.readInt();
            freqMap.put(value, freq);
        }
        return freqMap;
    }

    public static void compress(String inputFile, String outputFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append('\n');
        }
        br.close();
        String text = sb.toString();
        // Build frequency map and Huffman tree
        HashMap<Integer, Integer> freqMap = buildFrequencyMap(text);
        Node root = buildHuffmanTree(freqMap);
        HashMap<Integer, String> codeMap = new HashMap<>();
        generateCodes(root, "", codeMap);

        // Encode text using the codebook
        StringBuilder encodedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encodedText.append(codeMap.get((int)ch));
        }

        // Append the EOF marker code to signal end of data
        encodedText.append(codeMap.get(256));

        // Write header and encoded data to binary file
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFile));
        // Write frequency map header
        writeHeader(dos, freqMap);
        // Write the length of the encoded bit string (needed to trim padding during decompression)
        dos.writeInt(encodedText.length());
        // Pack the bit string into bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int len = encodedText.length();
        int i = 0;
        while (i < len) {
            int byteVal = 0;
            for (int bit = 0; bit < 8; bit++) {
                byteVal <<= 1;
                if (i < len && encodedText.charAt(i) == '1') {
                    byteVal |= 1;
                }
                i++;
            }
            baos.write(byteVal);
        }

        byte[] compressedBytes = baos.toByteArray();
        // Write the number of compressed bytes and then the bytes
        dos.writeInt(compressedBytes.length);
        dos.write(compressedBytes);
        dos.close();

        File inFile = new File(inputFile);
        File outFile = new File(outputFile);
        System.out.println("Original file size: " + inFile.length() + " bytes");
        System.out.println("Compressed file size: " + outFile.length() + " bytes");
    }

    public static void decompress(String inputFile, String outputFile) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(inputFile));
        // Read header to obtain the frequency map
        HashMap<Integer, Integer> freqMap = readHeader(dis);
        Node root = buildHuffmanTree(freqMap);

        // Read the length of the encoded bit string
        int encodedLength = dis.readInt();

        // Read compressed bytes
        int byteArrayLength = dis.readInt();
        byte[] compressedBytes = new byte[byteArrayLength];
        dis.readFully(compressedBytes);
        dis.close();

        StringBuilder encodedText = new StringBuilder();
        for (byte b : compressedBytes) {
            String byteStr = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            encodedText.append(byteStr);
        }
        // Trim extra padding bits
        encodedText.setLength(encodedLength);
        StringBuilder decodedText = new StringBuilder();
        Node current = root;
        for (int i = 0; i < encodedText.length(); i++) {
            char bit = encodedText.charAt(i);
            current = (bit == '0') ? current.left : current.right;
            if (current.isLeaf()) {
                if (current.value == 256) { // EOF marker reached
                    break;
                }
                decodedText.append((char)current.value);
                current = root;
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        bw.write(decodedText.toString());
        bw.close();
        System.out.println("Decompression complete. Output written to " + outputFile);
    }
    public static void main(String[] args) {
        try {
            compress("in.txt", "out.bin");
        }catch (Exception e) {
            System.out.println("Error");
        }
    }
}
