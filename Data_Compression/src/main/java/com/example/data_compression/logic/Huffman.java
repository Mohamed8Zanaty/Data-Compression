package com.example.data_compression.logic;
import java.util.HashMap;
public class Huffman {
    private  class HuffmanNode {
        public int value;
        public int frequency;
        public HuffmanNode left, right;
        HuffmanNode(int value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }
    }
    
    // maro
    public Map<Integer, Integer> getPixelFrequencies(int[][] image) {
        if (image == null || image.length == 0) {
            throw new IllegalArgumentException("Mat7otesh 7aga fadya");
        }
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int[] row : image) {
            for (int pixel : row) {
                frequencyMap.put(pixel, frequencyMap.getOrDefault(pixel, 0) + 1);
            }
        }
        return frequencyMap;
    }

    public static int compareHuffmanNodes(HuffmanNode a, HuffmanNode b) {
        return a.frequency - b.frequency;
    }

    public HuffmanNode buildHuffmanTree(Map<Integer, Integer> frequencyMap) {
        if (frequencyMap == null || frequencyMap.isEmpty()) {
            throw new IllegalArgumentException("Cringe Man!");
        }
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(Huffman::compareHuffmanNodes);
        // Create a Huffman node for each unique pixel value and add to priority queue
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Build the Huffman tree by merging the two least frequent nodes
        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();

            // Create a parent node with combined frequency
            HuffmanNode parent = new HuffmanNode(-1, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            queue.add(parent);
        }

        return queue.poll(); // Root of the Huffman tree
    }
        // ashraf
        public static String encodeText(String text, HashMap<character, String> huffmanCode) {
            StringBuilder encodedString = new StringBuilder();
            for (char ch : text.toCharArray()) {
                encodedString.append(huffmanCode.get(ch));
            }
            return encodedString.toString();
        }
        public static void decode(HoffmanNode root, int[] index, String s) {
            if (root == null) return;
            if (root.left == null && root.right == null) {
                System.out.print(root.character);
                return;
            }
            if (index[0] < s.length() - 1) {
                index[0]++;
                if (s.charAt(index[0]) == '0')
                    decode(root.left, index, s);
                else
                    decode(root.right, index, s);
            }
        }
    // fatma
    private static HashMap<int, String> generateCodes(HoffmanNode root){
        if (root==null)
            return null;
        HashMap<int, String> Codes = new HashMap<>();
        StringBuilder binaryCode = new StringBuilder();
        recursiveHelper(root,binaryCode,Codes);
        return Codes;
    }
    // fatma
    private static void recursiveHelper(HoffmanNode node,StringBuilder binaryCode,HashMap<int, String> Codes){
        if(node.left==null && node.right==null){
            Codes.put(node.value,binaryCode.toString());
            return;
        }
        binaryCode.append("1");
        recursiveHelper(node.right,binaryCode,Codes);
        int length = binaryCode.length();
        binaryCode.deleteCharAt(length-1);
        binaryCode.append("0");
        recursiveHelper(node.left,binaryCode,Codes);
        binaryCode.deleteCharAt(length-1);
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
