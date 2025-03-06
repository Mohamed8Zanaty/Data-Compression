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
   public void  generateHuffmanCodes(HoffmanNode curNode, String binCode, Map<Integer, String> pixelsCode){
        if(curNode.right==null||curNode.left==null){
            pixelsCode.put(curNode.frequency,binCode);
            return;
        }
        if(curNode.right!=null){
            generateHuffmanCodes( curNode.right,  binCode+="1",  pixelsCode);
        }
       if(curNode.left!=null){
           generateHuffmanCodes( curNode.left,  binCode+="0",  pixelsCode);
       }

    }

}
